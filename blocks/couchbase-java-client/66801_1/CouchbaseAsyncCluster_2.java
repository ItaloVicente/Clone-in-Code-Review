        try {
            Credential cred = getCredential(CredentialContext.BUCKET_KV, name);
            return openBucket(cred.getLogin(), cred.getPassword());
        } catch (IllegalArgumentException e) {
            return Observable.error(e);
        }
