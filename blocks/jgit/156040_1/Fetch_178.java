	private final GitImpl git;
	private final CredentialsProvider credentialsProvider;
	private final Map.Entry<String
	private final Collection<RefSpec> refSpecs;

	public Fetch(final GitImpl git
		this.git = git;
		this.credentialsProvider = credentialsProvider;
		this.refSpecs = refSpecs;
		this.remote = new AbstractMap.SimpleEntry<>("origin"
	}

	public Fetch(final GitImpl git
			final Map.Entry<String
		this.git = git;
		this.credentialsProvider = credentialsProvider;
		this.remote = remote;
		this.refSpecs = refSpecs;
	}

	public void execute() throws InvalidRemoteException {
		try {
			final List<RefSpec> specs = git.updateRemoteConfig(remote

			git._fetch().setCredentialsProvider(credentialsProvider).setRemote(remote.getKey()).setRefSpecs(specs)
					.call();
		} catch (final InvalidRemoteException e) {
			throw e;
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}
