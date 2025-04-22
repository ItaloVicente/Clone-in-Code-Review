	public List<Step> readSteps() throws IOException
			WrongRepositoryStateException {
		if (repo.getRepositoryState() == RepositoryState.REBASING_INTERACTIVE)
			return loadSteps();
		throw new WrongRepositoryStateException(MessageFormat.format(JGitText
				.get().wrongRepositoryState
	}

