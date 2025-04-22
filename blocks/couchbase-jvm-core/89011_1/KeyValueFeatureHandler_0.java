    private FullBinaryMemcacheRequest helloRequest(int connId) {
        byte[] key = generateAgentJson(
            ctx.environment().userAgent(),
            ctx.coreId(),
            connId
        ).getBytes(CharsetUtil.UTF_8);
