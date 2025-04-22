    
    protected Credential getCredential(CredentialContext context, String specific) {
        if (environment.authenticator() == null) {
            throw new IllegalStateException("Calling clusterManager without credentials nor an Authenticator");
        }
        List<Credential> creds = environment.authenticator().getCredentials(context, specific);
        if (creds == null || creds.size() != 1) {
            throw new IllegalStateException("Expected exactly 1 credential in Authenticator for clusterManager");
        }
        
        Credential cred = creds.get(0);
        return cred;
    }
    
    @Override
    public Observable<AsyncClusterManager> clusterManager() {
        try {
            Credential cred = getCredential(CredentialContext.CLUSTER_MANAGEMENT, null);
            return clusterManager(cred.getLogin(), cred.getPassword());
        } catch (IllegalArgumentException e) {
            return Observable.error(e);
        }
    }
