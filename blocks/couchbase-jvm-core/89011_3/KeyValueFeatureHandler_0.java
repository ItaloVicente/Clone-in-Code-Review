    private FullBinaryMemcacheRequest helloRequest(int connId) throws Exception {
        byte[] key = generateAgentJson(
            ctx.environment().userAgent(),
            ctx.coreId(),
            connId
        );
