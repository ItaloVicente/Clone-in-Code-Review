    public static void cleanup() throws Exception {
        if (ctx != null) {
            cluster.disconnect();
            roCluster.disconnect();
            ctx.clusterManager().removeUser(username);
            ctx.clusterManager().removeUser(roUsername);
            ctx.destroyBucketAndDisconnect();
        }
