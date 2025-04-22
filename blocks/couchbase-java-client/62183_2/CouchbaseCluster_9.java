
    @Override
    public CredentialsManager credentialsManager() {
        return couchbaseAsyncCluster.credentialsManager();
    }

    @Override
    public void setCredentialsManager(CredentialsManager newManager) {
        couchbaseAsyncCluster.setCredentialsManager(newManager);
    }
