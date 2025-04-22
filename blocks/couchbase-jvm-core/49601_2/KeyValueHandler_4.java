                    bucket, request);
        }

        return response;
    }

    private static void maybeFreeContent(BinaryRequest request) {
        ByteBuf content = null;
        if (request instanceof BinaryStoreRequest) {
            content = ((BinaryStoreRequest) request).content();
