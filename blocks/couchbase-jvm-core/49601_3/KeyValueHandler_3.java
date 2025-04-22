        } else if (request instanceof AppendRequest) {
            response = new AppendResponse(status, cas, bucket, content, request);
        } else if (request instanceof PrependRequest) {
            response = new PrependResponse(status, cas, bucket, content, request);
        } else if (request instanceof KeepAliveRequest) {
            releaseContent(content);
            response = new KeepAliveResponse(status, request);
        } else if (request instanceof CounterRequest) {
            long value = status.isSuccess() ? content.readLong() : 0;
            releaseContent(content);
            response = new CounterResponse(status, bucket, value, cas, request);
