    protected Credential getSingleCredential(CredentialContext context, String specific) {
        if (this.authenticator == null || this.authenticator.isEmpty()) {
            throw new AuthenticatorException("Attempted an authenticated operation with no Authenticator, or an empty Authenticator", context, specific, 0);
        }
        List<Credential> creds = this.authenticator.getCredentials(context, specific);
        if (creds == null || creds.isEmpty()) {
            throw new AuthenticatorException("Authenticator doesn't contain a credential for this operation, expected 1", context, specific, 0);
        } else if (creds.size() != 1) {
            throw new AuthenticatorException("Authenticator returned more than 1 credentials for this operation, expected 1", context, specific, creds.size());
        }

        Credential cred = creds.get(0);
        return cred;
    }

    @Override
    public Observable<AsyncClusterManager> clusterManager() {
        try {
            Credential cred = getSingleCredential(CredentialContext.CLUSTER_MANAGEMENT, null);
            return clusterManager(cred.login(), cred.password());
        } catch (AuthenticatorException e) {
            return Observable.error(e);
        }
    }

