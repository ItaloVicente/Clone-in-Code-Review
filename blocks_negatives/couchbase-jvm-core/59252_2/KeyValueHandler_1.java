        long cas = msg.getCAS();
        short statusCode = msg.getStatus();
        String bucket = request.bucket();

        MutationToken mutationToken = null;
        if (msg.getExtrasLength() > 0) {
            mutationToken = extractToken(bucket, seqOnMutation, status.isSuccess(), msg.getExtras(), request.partition());
        }

        ByteBuf body = msg.content();
        MultiMutationResponse response;
        if (status.isSuccess()) {
            response = new MultiMutationResponse(bucket, subdocRequest, cas, mutationToken);
        } else if (ResponseStatus.SUBDOC_MULTI_PATH_FAILURE.equals(status)) {
            short firstErrorCode = body.readShort();
            byte firstErrorIndex = body.readByte();
            response = new MultiMutationResponse(status, statusCode, bucket, firstErrorIndex, firstErrorCode,
                    subdocRequest, cas, mutationToken);
        } else {
            response = new MultiMutationResponse(status, statusCode, bucket, subdocRequest, cas, mutationToken);
        }
        body.release();
        return response;
