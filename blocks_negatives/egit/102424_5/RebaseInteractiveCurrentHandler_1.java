
		String currentBranch = getCurrentBranch(repository);
		final Ref ref = getRef(commit, repository, currentBranch);

		String jobname = NLS.bind(
				UIText.RebaseCurrentRefCommand_RebasingCurrentJobName,
				currentBranch, ref.getName());
		AbstractRebaseCommandHandler rebaseCurrentRef = new AbstractRebaseCommandHandler(
				jobname, UIText.RebaseCurrentRefCommand_RebaseCanceledMessage) {
			@Override
			protected RebaseOperation createRebaseOperation(
					Repository repository2) throws ExecutionException {
				return new RebaseOperation(repository2, ref,
						RebaseInteractiveHandler.INSTANCE);
			}
		};
		rebaseCurrentRef.execute(repository);
