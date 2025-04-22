    private final HashAlgorithm hashAlgorithm;

    private final int vbucketsCount;

    private final int mask;

    private final int serversCount;

    private final int replicasCount;

    private final List<String> servers;

    private final List<VBucket> vbuckets;

    public DefaultConfig(HashAlgorithm hashAlgorithm, int serversCount,
            int replicasCount, int vbucketsCount, List<String> servers,
            List<VBucket> vbuckets) {
        this.hashAlgorithm = hashAlgorithm;
        this.serversCount = serversCount;
        this.replicasCount = replicasCount;
        this.vbucketsCount = vbucketsCount;
        this.mask = vbucketsCount - 1;
        this.servers = servers;
        this.vbuckets = vbuckets;
    }

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
