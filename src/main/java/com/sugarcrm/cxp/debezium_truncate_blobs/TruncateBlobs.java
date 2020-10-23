package com.sugarcrm.cxp.debezium_truncate_blobs;

import io.debezium.spi.converter.CustomConverter;
import io.debezium.spi.converter.RelationalColumn;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.connect.data.SchemaBuilder;

public class TruncateBlobs implements CustomConverter<SchemaBuilder, RelationalColumn> {

    private static final List<String> BLOB_TYPES = List.of("TINYBLOB", "BLOB", "MEDIUMBLOB", "LONGBLOB");

    public Boolean debug;

    @Override
    public void configure(Properties props) {
        System.out.println("configuring TruncateBlobs");
        this.debug = props.getProperty("debug", "false").equals("true");

        if (this.debug) {
            System.out.printf(
                    "[TruncateBlobs.configure] Finished configuration: debug - %s%n",
                    this.debug
            );
        }
    }

    @Override
    public void converterFor(RelationalColumn column, ConverterRegistration<SchemaBuilder> registration) {
        if (this.debug) {
            System.out.printf(
                    "[TruncateBlobs.converterFor]: Converter registration requested for column name %s, type %s%n",
                    column.name(),
                    column.typeName()
            );
        }

        String columnType = column.typeName().toUpperCase();
        if (BLOB_TYPES.stream().anyMatch(t -> t.equals(columnType))) {
            registration.register(SchemaBuilder.bytes().optional(), v -> null);

            if (this.debug) {
                System.out.println("[TruncateBlobs.converterFor]: Converter registration request fulfilled");
            }
        }
    }
}