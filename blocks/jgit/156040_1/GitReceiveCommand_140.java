	private final ReceivePackFactory<BaseGitCommand> receivePackFactory;

	public GitReceiveCommand(final String command
			final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver
			final ReceivePackFactory<BaseGitCommand> receivePackFactory
		super(command
		this.receivePackFactory = receivePackFactory;
	}

	@Override
	protected String getCommandName() {
		return "git-receive-pack";
	}

	@Override
	protected void execute(final Repository repository
			final OutputStream err) {
		try {
			final ReceivePack rp = receivePackFactory.create(this
			rp.receive(in
			rp.setPostReceiveHook((rp1
				new Git(repository).gc();
			});
		} catch (final Exception ignored) {
		}
	}
