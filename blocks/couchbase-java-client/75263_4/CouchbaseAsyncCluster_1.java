                                              final List<Transcoder<? extends Document, ?>> transcoders) {
        if (this.authenticator != null && this.authenticator instanceof PasswordAuthenticator) {
            throw new MixedAuthenticationException("Mixed mode authentication not allowed");
        }

        return openBucketInternal(name, name, password, transcoders);
    }

    private Observable<AsyncBucket> openBucketInternal(final String name, final String username, final String password,
                                                       final List<Transcoder<? extends Document, ?>> transcoders) {
