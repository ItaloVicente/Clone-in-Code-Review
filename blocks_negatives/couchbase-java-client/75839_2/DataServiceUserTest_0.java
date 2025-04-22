    public static void tearDown() throws Exception {
        cluster.disconnect();
        roCluster.disconnect();
        ctx.clusterManager().removeUser(username);
        ctx.clusterManager().removeUser(roUsername);
        ctx.destroyBucketAndDisconnect();
