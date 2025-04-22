        } catch (MalformedMemcacheHeaderException e) {
            LOGGER.error(logIdent(ctx, endpoint) +
                    "Closing and reconnecting the endpoint due to malformed header, reason is "+ e.getMessage());
            endpoint().disconnect();
            endpoint().connect();
            responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, currentRequest, currentRequest.observable());
