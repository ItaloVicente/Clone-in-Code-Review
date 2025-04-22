	private String getShortBranch() throws IOException {
		Ref head = repository.getRef(Constants.HEAD);
		if (head != null && !head.isSymbolic()) {
			String refString = Activator.getDefault().getRepositoryUtil()
					.mapCommitToRef(repository, repository.getFullBranch(),
							false);
			if (refString != null) {
				return repository.getFullBranch().substring(0, 7)
			} else
				return repository.getFullBranch().substring(0, 7) + "..."; //$NON-NLS-1$
		}

		return repository.getBranch();
	}

