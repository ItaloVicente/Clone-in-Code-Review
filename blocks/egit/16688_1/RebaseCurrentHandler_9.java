		RebaseCurrentRefCommandHandler rebaseCurrent = new RebaseCurrentRefCommandHandler() {
			@Override
			public RebaseOperation getRebaseOperation(ExecutionEvent event2)
					throws ExecutionException {
				return new RebaseOperation(repository, ref);
			}
			@Override
			public String getJobName(RebaseOperation operation) {
				return jobname;
			}
		};
		return rebaseCurrent.execute(event);
