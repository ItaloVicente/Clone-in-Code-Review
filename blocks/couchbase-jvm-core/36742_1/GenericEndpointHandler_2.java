            if (currentRequest == null) {
                currentRequest = queue.poll();
            }

            responseBuffer.publishEvent(RESPONSE_TRANSLATOR, in, currentRequest.observable());
            if (in.status() != ResponseStatus.CHUNKED) {
                currentRequest = null;
            }
