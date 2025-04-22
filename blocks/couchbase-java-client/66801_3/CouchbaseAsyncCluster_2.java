        try {
            Credential cred = getSingleCredential(CredentialContext.BUCKET_KV, name);
            return openBucket(cred.login(), cred.password());
        } catch (IllegalArgumentException e) {
            return Observable.error(e);
        }
