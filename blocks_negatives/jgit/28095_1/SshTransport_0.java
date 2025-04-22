    /**
     * Create a new transport instance without a local repository.
     *
     * @param uri
     *            the URI used to access the remote repository. This must be the
     *            URI passed to {@link #open(URIish)}.
     */
    protected SshTransport(URIish uri) {
        super(uri);
        sch = SshSessionFactory.getInstance();
    }
