            currentRequest = sentRequestQueue.poll();
            currentDecodingState = DecodingState.STARTED;
            if (currentRequest != null) {
                Long st = sentRequestTimings.poll();
                if (st != null) {
                    currentOpTime = System.nanoTime() - st;
                } else {
                    currentOpTime = -1;
                }
            }

            if (traceEnabled) {
                LOGGER.trace("{}Started decoding of {}", logIdent(ctx, endpoint), currentRequest);
            }
