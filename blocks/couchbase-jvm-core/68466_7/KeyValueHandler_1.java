        if (msg.getDecoderResult().isFailure()) {
            Throwable cause = msg.getDecoderResult().cause();
            if (cause instanceof MalformedMemcacheHeaderException) {
                throw (MalformedMemcacheHeaderException)cause;
            }
        }

