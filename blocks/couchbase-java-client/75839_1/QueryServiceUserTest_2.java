    public static void cleanup() throws Exception {
        if (ctx != null) {
            cluster.disconnect();
            ctx.clusterManager().removeUser(username);
            ctx.destroyBucketAndDisconnect();
        }
