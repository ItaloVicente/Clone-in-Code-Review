		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				return target.pull().call();
			}
		};
