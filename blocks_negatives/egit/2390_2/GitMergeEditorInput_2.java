		this.compareResult = new DiffNode(Differencer.CONFLICTING);

		final RevWalk rw = new RevWalk(repo);

		final RevCommit rightCommit;
		try {
			String target;
			if (repo.getRepositoryState().equals(RepositoryState.MERGING))
				target = Constants.MERGE_HEAD;
			else if (repo.getRepositoryState().equals(
					RepositoryState.REBASING_INTERACTIVE))
				target = readFile(repo.getDirectory(),
						RebaseCommand.REBASE_MERGE + File.separatorChar
								+ RebaseCommand.STOPPED_SHA);
			else
				target = Constants.ORIG_HEAD;
			ObjectId mergeHead = repo.resolve(target);
			if (mergeHead == null)
				throw new IOException(NLS.bind(
						UIText.ValidationUtils_CanNotResolveRefMessage, target));
			rightCommit = rw.parseCommit(mergeHead);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}

		final RevCommit headCommit;
		try {
			ObjectId head = repo.resolve(Constants.HEAD);
			if (head == null)
				throw new IOException(NLS.bind(
						UIText.ValidationUtils_CanNotResolveRefMessage,
						Constants.HEAD));
			headCommit = rw.parseCommit(head);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}

		final String fullBranch;
		try {
			fullBranch = repo.getFullBranch();
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}
