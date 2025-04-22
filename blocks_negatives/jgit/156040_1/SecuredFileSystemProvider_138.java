    /**
     * Sets the authenticator that decides which username/password pairs are valid for the file systems managed by this
     * provider.
     * @param authenticator The authenticator to use. Must not be null.
     */
    public abstract void setJAASAuthenticator(final AuthenticationService authenticator);
