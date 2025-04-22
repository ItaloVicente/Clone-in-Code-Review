        if (this.authenticator != null  && this.authenticator.getClass() != auth.getClass()) {
            throw new MixedAuthenticationException("Mixed mode authentication not allowed, use either Classic Authenticator " +
                    "or Password Authenticator");
        }

        if (this.authenticator instanceof PasswordAuthenticator) {
            PasswordAuthenticator pa = (PasswordAuthenticator)this.authenticator;
            PasswordAuthenticator newPa = (PasswordAuthenticator)auth;
            if (newPa.username() == null) {
                auth = new PasswordAuthenticator(pa.username(), newPa.password());
            }
        }

