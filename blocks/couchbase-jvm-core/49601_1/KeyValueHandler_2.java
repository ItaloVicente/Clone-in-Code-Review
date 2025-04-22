        }

        return response;
    }


    private static CouchbaseResponse handleOtherResponseMessages(BinaryRequest request, FullBinaryMemcacheResponse msg,
        ResponseStatus status) {
        CouchbaseResponse response = null;
        ByteBuf content = msg.content();
        long cas = msg.getCAS();
        String bucket = request.bucket();

        if (request instanceof UnlockRequest) {
