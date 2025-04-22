        } else if (request instanceof StatRequest) {
            String key = msg.getKey();
            String value = content.toString(CHARSET);
            releaseContent(content);

            response = new StatResponse(status, statusCode, remoteHostname, key, value, bucket, request);
