		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("branch", "master", "rebase", "true");
				config.save();
				return target.pull().call();
			}
		};
