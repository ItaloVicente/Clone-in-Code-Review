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
					.setObjectId(getTarget())
					.setTagger(getTagger());
			if (!isAnnotated()) {
				setMessage(null);
				if (Boolean.FALSE.equals(sign)) {
					command.setAnnotated(false);
				} else if (sign == null) {
					GpgConfig config = new GpgConfig(repository.getConfig());
					if (!config.isSignAllTags()) {
						command.setAnnotated(false);
					}
				}
			}
			command.setMessage(getMessage());
			if (sign != null) {
				command.setSigned(sign.booleanValue());
