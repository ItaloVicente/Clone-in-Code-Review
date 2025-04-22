        Credential cred = new Credential(name, null);
        try {
            cred = getSingleCredential(CredentialContext.BUCKET_KV, name);
        } catch (AuthenticatorException e) {
            if (e.foundCredentials() > 1) {
                return Observable.error(e);
            }
        }

        return openBucket(cred.login(), cred.password());
