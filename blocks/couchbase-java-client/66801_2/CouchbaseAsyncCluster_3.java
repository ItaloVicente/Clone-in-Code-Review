    protected Credential getSingleCredential(CredentialContext context, String specific) {
        if (this.authenticator == null) {
            throw new IllegalStateException("Attempted an authenticated operation without credentials nor an Authenticator");
        }
        List<Credential> creds = this.authenticator.getCredentials(context, specific);
        if (creds == null || creds.size() != 1) {
            throw new IllegalStateException("Expected exactly 1 credential in Authenticator for this operation");
        }
        
        Credential cred = creds.get(0);
        return cred;
    }
    
    @Override
    public Observable<AsyncClusterManager> clusterManager() {
        try {
            Credential cred = getSingleCredential(CredentialContext.CLUSTER_MANAGEMENT, null);
            return clusterManager(cred.login(), cred.password());
        } catch (IllegalArgumentException e) {
            return Observable.error(e);
        }
    }

