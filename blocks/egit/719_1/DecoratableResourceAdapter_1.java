		if (head != null && !head.isSymbolic()) {
			String refString = Activator.getDefault().getRepositoryUtil()
					.mapCommitToRef(repository, repository.getFullBranch(),
							false);
			if (refString != null) {
				return repository.getFullBranch().substring(0, 7)
						+ "... (" + refString + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			} else
				return repository.getFullBranch().substring(0, 7) + "..."; //$NON-NLS-1$
		}
