
    @Override
    public int getReplicasCount() {
        return replicasCount;
    }

    @Override
    public int getVbucketsCount() {
        return vbucketsCount;
    }

    @Override
    public int getServersCount() {
        return serversCount;
    }

    @Override
    public String getServer(int serverIndex) {
        return servers.get(serverIndex);
    }

    @Override
    public int getVbucketByKey(String key) {
        int digest = (int)hashAlgorithm.hash(key);
        return digest & mask;
    }

    @Override
    public int getMaster(int vbucketIndex) {
        return vbuckets.get(vbucketIndex).getMaster();
    }

    @Override
    public int getReplica(int vbucketIndex, int replicaIndex) {
        return vbuckets.get(vbucketIndex).getReplica(replicaIndex);
    }

    @Override
    public int foundIncorrectMaster(int vbucket, int wrongServer) {
        int mappedServer = this.vbuckets.get(vbucket).getMaster();
        int rv = mappedServer;
        if (mappedServer == wrongServer) {
            rv = (rv + 1) % this.serversCount;
            this.vbuckets.get(vbucket).setMaster(rv);
