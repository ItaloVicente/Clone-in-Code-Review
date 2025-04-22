            if (lastChunk) {
                IllegalStateException e = new IllegalStateException("Error parsing query response (in TRANSITION) at \""
                        + peek + "\", enable trace to see response content");
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace(responseContent.toString(CHARSET), e);
                }
                throw e;
            } else {
                return queryParsingState;
