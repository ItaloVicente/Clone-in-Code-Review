		AbstractRebaseCommandHandler rebaseCurrentRef = new AbstractRebaseCommandHandler(
				jobname, UIText.RebaseCurrentRefCommand_RebaseCanceledMessage) {
			@Override
			protected RebaseOperation createRebaseOperation(
					Repository repository2) throws ExecutionException {
				return new RebaseOperation(repository2, ref);
			}
		};
		rebaseCurrentRef.execute(repository);
