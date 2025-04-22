	private final Git git;
	private final Map.Entry<String
	private final Collection<RefSpec> refSpecs;

	public UpdateRemoteConfig(final Git git
			final Collection<RefSpec> refSpecs) {
		this.git = git;
		this.remote = remote;
		this.refSpecs = refSpecs;
	}

	public List<RefSpec> execute() throws IOException
		final List<RefSpec> specs = new ArrayList<>();
		if (refSpecs == null || refSpecs.isEmpty()) {
		} else {
			specs.addAll(refSpecs);
		}

		final StoredConfig config = git.getRepository().getConfig();
		final String url = config.getString("remote"
		if (url == null) {
			final RemoteConfig remoteConfig = new RemoteConfig(git.getRepository().getConfig()
			remoteConfig.addURI(new URIish(remote.getValue()));
			specs.forEach(remoteConfig::addFetchRefSpec);
			remoteConfig.update(git.getRepository().getConfig());
			git.getRepository().getConfig().save();
		}
		return specs;
	}
