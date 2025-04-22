        return content;
    }

    private static void resetContentReaderIndex(BinaryRequest request) {
        ByteBuf content = contentFromWriteRequest(request);
        if (content != null) {
            try {
                content.readerIndex(0);
            } catch (Exception ex) {
                LOGGER.warn("Exception while resetting the content reader index to 0, " +
                    "please report this as a bug.", ex);
            }
        }
