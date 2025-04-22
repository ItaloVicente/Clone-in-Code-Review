        Credential cred = new Credential(name, null); //the old default
        try {
            cred = couchbaseAsyncCluster.getSingleCredential(CredentialContext.BUCKET_KV, name);
        } catch (AuthenticatorException e) {
            if (e.foundCredentials() > 1) {
                throw e;
            }
        }
        return openBucket(cred.login(), cred.password(), timeout, timeUnit);
