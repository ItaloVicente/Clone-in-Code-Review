		Git git = new Git(repo);

		Calendar cal = Calendar.getInstance();
		long time = cal.getTime().getTime();
		PersonIdent sideCommitter = new PersonIdent("Side Committer",
				"side@example.org", time, 0);
		time += 5000;
		PersonIdent masterCommitter = new PersonIdent("Master Committer",
				"master@example.org", time, 0);

		git.checkout().setCreateBranch(true).setName("side").call();
		touch(PROJ1, "folder/test.txt", "side");
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("Side commit").setCommitter(sideCommitter).call();

		git.checkout().setName("master").call();
		touch(PROJ1, "folder/test2.txt", "master");
		git.commit().setAll(true).setMessage("Master commit")
				.setCommitter(masterCommitter).call();

		git.merge().include(sideCommit).call();

