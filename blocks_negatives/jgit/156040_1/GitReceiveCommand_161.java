    private final ReceivePackFactory<BaseGitCommand> receivePackFactory;

    public GitReceiveCommand(final String command,
                             final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver,
                             final ReceivePackFactory<BaseGitCommand> receivePackFactory,
                             final ExecutorService executorService) {
        super(command,
              repositoryResolver,
              executorService);
        this.receivePackFactory = receivePackFactory;
    }

    @Override
    protected String getCommandName() {
        return "git-receive-pack";
    }

    @Override
    protected void execute(final Repository repository,
                           final InputStream in,
                           final OutputStream out,
                           final OutputStream err) {
        try {
            final ReceivePack rp = receivePackFactory.create(this, repository);
            rp.receive(in, out, err);
            rp.setPostReceiveHook((rp1, commands) -> {
                new Git(repository).gc();
            });
        } catch (final Exception ignored) {
        }
    }
