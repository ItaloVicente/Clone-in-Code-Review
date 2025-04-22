    private final GitImpl git;
    private final CredentialsProvider credentialsProvider;
    private final Map.Entry<String, String> remote;
    private final Collection<RefSpec> refSpecs;

    public Fetch(final GitImpl git,
                 final CredentialsProvider credentialsProvider,
                 final Collection<RefSpec> refSpecs) {
        this.git = git;
        this.credentialsProvider = credentialsProvider;
        this.refSpecs = refSpecs;
        this.remote = new AbstractMap.SimpleEntry<>("origin", null);
    }

    public Fetch(final GitImpl git,
                 final CredentialsProvider credentialsProvider,
                 final Map.Entry<String, String> remote,
                 final Collection<RefSpec> refSpecs) {
        this.git = git;
        this.credentialsProvider = credentialsProvider;
        this.remote = remote;
        this.refSpecs = refSpecs;
    }

    public void execute() throws InvalidRemoteException {
        try {
            final List<RefSpec> specs = git.updateRemoteConfig(remote,
                                                               refSpecs);

            git._fetch()
                    .setCredentialsProvider(credentialsProvider)
                    .setRemote(remote.getKey())
                    .setRefSpecs(specs)
                    .call();
        } catch (final InvalidRemoteException e) {
            throw e;
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
