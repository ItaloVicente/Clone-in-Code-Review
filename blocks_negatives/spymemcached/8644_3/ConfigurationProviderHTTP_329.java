    public List<InetSocketAddress> getServerList(final String bucketname) throws ConfigurationException {
        Bucket bucket = getBucketConfiguration(bucketname);
        List<String> servers = bucket.getConfig().getServers();
        StringBuilder serversString = new StringBuilder();
        for (String server : servers) {
            serversString.append(server).append(' ');
        }
        return AddrUtil.getAddresses(serversString.toString());
