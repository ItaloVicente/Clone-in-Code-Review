            try {
                shouldDecodeChunked(chunk1, chunk2);
            } catch (Throwable t) {
                LOGGER.info("Test failed in decoding response with chunk at position " + i);
                throw t;
            }
