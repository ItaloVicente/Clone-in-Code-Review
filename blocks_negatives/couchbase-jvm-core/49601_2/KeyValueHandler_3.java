            response = new PrependResponse(status, cas, bucket, content, request);
        } else if (request instanceof KeepAliveRequest) {
            if (content != null && content.refCnt() > 0) {
                content.release();
            }
            response = new KeepAliveResponse(status, request);
        } else {
            throw new IllegalStateException("Unhandled request/response pair: " + request.getClass() + "/"
                + msg.getClass());
