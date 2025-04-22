    /**
     * Create a new transport instance without a local repository.
     *
     * @param uri
     *            the URI used to access the remote repository. This must be the
     *            URI passed to {@link #open(URIish)}.
     */
    protected TcpTransport(URIish uri) {
        super(uri);
    }

