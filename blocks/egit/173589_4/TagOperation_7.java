	public CredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 1);
		progress.setTaskName(MessageFormat
				.format(CoreText.TagOperation_performingTagging, getName()));
		try (Git git = new Git(repository)) {
			TagCommand command = git.tag()
					.setName(getName())
					.setForceUpdate(isForce())
					.setObjectId(getTarget());
			if (!isAnnotated()) {
				if (Boolean.FALSE.equals(sign)) {
					setMessage(null);
					setTagger(null);
					command.setAnnotated(false);
				} else if (sign == null) {
					GpgConfig config = new GpgConfig(repository.getConfig());
					if (!config.isSignAllTags()) {
						setMessage(null);
						setTagger(null);
						command.setAnnotated(false);
					}
				}
