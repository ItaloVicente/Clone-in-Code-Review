		RebaseCurrentRefCommand rebaseCurrentRef = new RebaseCurrentRefCommand(
				jobname) {
			@Override
			public RebaseOperation createRebaseOperation(ExecutionEvent event2)
					throws ExecutionException {
				return new RebaseOperation(repository, ref);
			}
		};
		rebaseCurrentRef.execute(event);
