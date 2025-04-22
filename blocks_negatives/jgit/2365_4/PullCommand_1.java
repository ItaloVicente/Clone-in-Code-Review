
		if (monitor.isCancelled())
			throw new CanceledException(MessageFormat.format(
					JGitText.get().operationCanceled,
					JGitText.get().pullTaskName));

		MergeCommand merge = new MergeCommand(repo);
		merge.include("branch \'" + remoteBranchName + "\' of " + remoteUri,
				commitToMerge);
		MergeResult mergeRes;
		try {
			mergeRes = merge.call();
			monitor.update(1);
		} catch (NoHeadException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (ConcurrentRefUpdateException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (CheckoutConflictException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (InvalidMergeHeadsException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (WrongRepositoryStateException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (NoMessageException e) {
			throw new JGitInternalException(e.getMessage(), e);
		}
