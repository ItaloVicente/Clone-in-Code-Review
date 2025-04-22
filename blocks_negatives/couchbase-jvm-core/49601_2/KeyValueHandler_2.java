        } else if (request instanceof CounterRequest) {
            long value = status.isSuccess() ? content.readLong() : 0;
            if (content != null && content.refCnt() > 0) {
                content.release();
            }
            response = new CounterResponse(status, bucket, value, cas, request);
        } else if (request instanceof UnlockRequest) {
