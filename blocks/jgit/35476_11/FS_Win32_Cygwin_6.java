
	@Override
	public String relativize(String base
		final String relativized = super.relativize(base
		return relativized.replace(File.separatorChar
	}

	@Override
	public ProcessResult runIfPresent(Repository repository
			String[] args
			String stdinArgs) throws JGitInternalException {
		return internalRunIfPresent(repository
				errRedirect
	}
