    public static void cleanup() throws Exception {
        if (ctx != null) {
            cluster.disconnect();
            clusterWithNoFtsPerms.disconnect();
            ctx.clusterManager().removeUser(username);
            ctx.clusterManager().removeUser(usernameWithNoPerms);
            ctx.destroyBucketAndDisconnect();
        }
