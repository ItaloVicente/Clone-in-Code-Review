		AbstractRebaseCommandHandler rebaseCurrentRef = new AbstractRebaseCommandHandler(
				jobname, UIText.RebaseCurrentRefCommand_RebaseCanceledMessage) {
			@Override
			protected RebaseOperation createRebaseOperation(
					ExecutionEvent event2) throws ExecutionException {
				return new RebaseOperation(repository, ref);
			}
		};
		rebaseCurrentRef.execute(event);
