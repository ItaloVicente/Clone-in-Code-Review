		Git git = new Git(repo);
		git.branchCreate().setName("test3").setStartPoint("refs/heads/master")
				.setUpstreamMode(SetupUpstreamMode.TRACK).call();
		git.checkout().setName("test3").call();
		repo.renameRef(R_HEADS + "test3", Constants.R_REMOTES + "origin/master").rename();
		GitSynchronizeData gsd = new GitSynchronizeData(repo, "refs/remotes/origin/master",
				HEAD, false);
