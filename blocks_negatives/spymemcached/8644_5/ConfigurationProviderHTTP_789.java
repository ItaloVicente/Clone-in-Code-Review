        return AddrUtil.getAddresses(serversString.toString());
    }

    /**
     * Subscribes for configuration updates
     * @param bucketName bucket name to receive configuration for
     * @param rec reconfigurable that will receive updates
     * @throws ConfigurationException
     */
    public void subscribe(String bucketName, Reconfigurable rec) throws ConfigurationException {
        Bucket bucket = getBucketConfiguration(bucketName);
