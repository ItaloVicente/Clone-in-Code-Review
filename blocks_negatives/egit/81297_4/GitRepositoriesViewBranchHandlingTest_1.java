		Git git = new Git(repo);
		git.branchCreate().setName("configTest")
				.setStartPoint("refs/remotes/origin/master")
				.setUpstreamMode(SetupUpstreamMode.TRACK).call();

		boolean rebase = repo.getConfig().getBoolean(
