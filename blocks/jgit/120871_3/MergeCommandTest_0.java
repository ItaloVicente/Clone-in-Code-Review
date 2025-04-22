	private Ref prepareSuccessfulMerge(Git git) throws Exception {
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		writeTrashFile("c"
		git.add().addFilepattern("c").call();
		git.commit().setMessage("main").call();

		return db.exactRef("refs/heads/side");
	}

