    protected void addCredentialsIfNeeded(N1qlQuery query) {
        if (authenticator == null)
            return;

        List<Credential> creds = authenticator.getCredentials(CredentialContext.CLUSTER_N1QL, null);
        if (creds == null || creds.isEmpty())
            return;

        if (creds.size() > 1 || !bucket.equals(creds.get(0).login())) {
            query.params().withCredentials(creds);
        }
    }

