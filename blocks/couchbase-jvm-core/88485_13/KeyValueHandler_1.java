        if (hasCompressionDatatype(msg.getDataType())) {
            if (!snappyEnabled) {
                LOGGER.debug("Snappy DataType bit set, but snappy has not been negotiated! " +
                    "Trying to decompress nonetheless!", msg);
            }
            handleSnappyDecompression(ctx, msg);
        }

