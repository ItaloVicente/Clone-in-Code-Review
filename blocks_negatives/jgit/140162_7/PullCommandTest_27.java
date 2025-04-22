		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull", null, "rebase", "preserve");
				config.save();
				return target.pull().call();
			}
