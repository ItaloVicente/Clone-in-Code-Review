            if (request.content().hasArray()) {
                compressedContent = Unpooled.wrappedBuffer(Snappy.compress(request.content().array()));
            } else {
                LOGGER.debug("Request content is not array backed, not encoding.");
                return;
            }
