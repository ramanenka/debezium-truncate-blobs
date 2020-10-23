# Truncate Blobs utility for Debezium

You can use it to nullify all blob data.

## Usage

**You have to add this converter to each of your connectors, not just in the main folder (`/kafka/connect`)!**

## Configuration

```properties
converters=truncateBlobs
truncateBlobs.type=com.sugarcrm.cxp.debezium_truncate_blobs.TruncateBlobs
truncateBlobs.debug=true
```
