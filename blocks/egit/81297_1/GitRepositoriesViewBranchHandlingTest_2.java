		try (Git git = new Git(repo)) {
			git.branchCreate().setName("configTest")
					.setStartPoint("refs/remotes/origin/master")
					.setUpstreamMode(SetupUpstreamMode.TRACK).call();
		}
		BranchRebaseMode rebase = repo.getConfig().getEnum(
				BranchRebaseMode.values(),
