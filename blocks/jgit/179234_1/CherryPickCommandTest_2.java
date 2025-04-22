	private RevCommit prepareCherryPickStrategyOption(Git git)
			throws Exception {
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit firstMasterCommit = git.commit().setMessage("first master")
				.call();

		createBranch(firstMasterCommit
		checkoutBranch("refs/heads/side");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit sideCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("second master").call();
		return sideCommit;
	}

