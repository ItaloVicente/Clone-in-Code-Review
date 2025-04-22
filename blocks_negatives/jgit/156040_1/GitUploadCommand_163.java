    private UploadPackFactory<BaseGitCommand> uploadPackFactory;

    public GitUploadCommand(final String command,
                            final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver,
                            final UploadPackFactory uploadPackFactory,
                            final ExecutorService executorService) {
        super(command,
              repositoryResolver,
              executorService);
        this.uploadPackFactory = uploadPackFactory;
    }

    @Override
    protected String getCommandName() {
        return "git-upload-pack";
    }

    @Override
    protected void execute(final Repository repository,
                           final InputStream in,
                           final OutputStream out,
                           final OutputStream err) {
        try {
            final UploadPack up = uploadPackFactory.create(this,
                                                           repository);

            final PackConfig config = new PackConfig(repository);
            config.setCompressionLevel(Deflater.BEST_COMPRESSION);
            up.setPackConfig(config);

            if (up.getRefFilter() == RefFilter.DEFAULT) {
                up.setRefFilter(new HiddenBranchRefFilter());
            }

            up.upload(in,
                      out,
                      err);
        } catch (final Exception ignored) {
        }
    }
