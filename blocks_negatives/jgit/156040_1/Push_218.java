    private final GitImpl git;
    private final CredentialsProvider credentialsProvider;
    private final Map.Entry<String, String> remote;
    private final boolean force;
    private final Collection<RefSpec> refSpecs;

    public Push(final Git git,
                final CredentialsProvider credentialsProvider,
                final Map.Entry<String, String> remote,
                final boolean force,
                final Collection<RefSpec> refSpecs) {
        this.git = checkInstanceOf("git",
                                   git,
                                   GitImpl.class);
        this.credentialsProvider = credentialsProvider;
        this.remote = checkNotNull("remote",
                                   remote);
        this.force = force;
        this.refSpecs = refSpecs;
    }

    public void execute() throws InvalidRemoteException {
        try {
            final List<RefSpec> specs = new UpdateRemoteConfig(git,
                                                               remote,
                                                               refSpecs).execute();
            git._push()
                    .setCredentialsProvider(credentialsProvider)
                    .setRefSpecs(specs)
                    .setRemote(remote.getKey())
                    .setForce(force)
                    .setPushAll()
                    .call();
        } catch (final InvalidRemoteException e) {
            throw e;
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
