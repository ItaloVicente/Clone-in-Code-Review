		RebaseCurrentRefCommand rebaseCurrentRef = new RebaseCurrentRefCommand() {
			@Override
			public RebaseOperation createRebaseOperation(ExecutionEvent event2)
					throws ExecutionException {
				this.jobname = name;
				return new RebaseOperation(repository, ref);
			}
		};
		rebaseCurrentRef.execute(event);
