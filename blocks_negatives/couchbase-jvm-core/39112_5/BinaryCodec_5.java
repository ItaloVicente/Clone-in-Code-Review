    protected void decode(final ChannelHandlerContext ctx, final FullBinaryMemcacheResponse msg,
        final List<Object> in) throws Exception {
        BinaryRequest current = queue.poll();

        ResponseStatus status = convertStatus(msg.getStatus());
        CouchbaseRequest currentRequest = null;
        if (status == ResponseStatus.RETRY) {
            currentRequest = current;
