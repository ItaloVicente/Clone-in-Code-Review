    public static void tearDown() throws Exception {
        cluster.disconnect();
        clusterWithNoFtsPerms.disconnect();
        ctx.clusterManager().removeUser(username);
        ctx.clusterManager().removeUser(usernameWithNoPerms);
        ctx.destroyBucketAndDisconnect();
