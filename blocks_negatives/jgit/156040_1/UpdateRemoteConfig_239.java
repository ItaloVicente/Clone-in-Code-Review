    private final Git git;
    private final Map.Entry<String, String> remote;
    private final Collection<RefSpec> refSpecs;

    public UpdateRemoteConfig(final Git git,
                              final Map.Entry<String, String> remote,
                              final Collection<RefSpec> refSpecs) {
        this.git = git;
        this.remote = remote;
        this.refSpecs = refSpecs;
    }

    public List<RefSpec> execute() throws IOException, URISyntaxException {
        final List<RefSpec> specs = new ArrayList<>();
        if (refSpecs == null || refSpecs.isEmpty()) {
            specs.add(new RefSpec("+refs/heads/*:refs/remotes/" + remote.getKey() + "/*"));
            specs.add(new RefSpec("+refs/tags/*:refs/tags/*"));
            specs.add(new RefSpec("+refs/notes/*:refs/notes/*"));
        } else {
            specs.addAll(refSpecs);
        }

        final StoredConfig config = git.getRepository().getConfig();
        final String url = config.getString("remote",
                                            remote.getKey(),
                                            "url");
        if (url == null) {
            final RemoteConfig remoteConfig = new RemoteConfig(git.getRepository().getConfig(),
                                                               remote.getKey());
            remoteConfig.addURI(new URIish(remote.getValue()));
            specs.forEach(remoteConfig::addFetchRefSpec);
            remoteConfig.update(git.getRepository().getConfig());
            git.getRepository().getConfig().save();
        }
        return specs;
    }
