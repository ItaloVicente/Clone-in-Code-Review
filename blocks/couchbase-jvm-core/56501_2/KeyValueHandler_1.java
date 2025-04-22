        } else if (request instanceof GetAllMutationTokensRequest) {
            MutationToken[] mutationTokens = new MutationToken[content.readableBytes() / 10];
            for (int i = 0; i < mutationTokens.length; i++) {
                mutationTokens[i] = new MutationToken((long)content.readShort(), 0, content.readLong());
            }
            releaseContent(content);
            response = new GetAllMutationTokensResponse(mutationTokens, status, statusCode, bucket, request);
