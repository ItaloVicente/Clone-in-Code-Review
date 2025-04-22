		git.commit().setMessage("files a & b modified commit 2").call();
		git.cherryPick().include(commit1).call();
		String[] conflictingFilenames = { "a"
		return conflictingFilenames;
	}

	protected String[] createDeletedConflict() throws Exception {
		git.checkout().setName(TEST_BRANCH_NAME).call();
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("files a & b added").call();
		git.branchCreate().setName("branch_1").call();
		git.checkout().setName("branch_1").call();
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		RevCommit commit1 = git.commit()
				.setMessage("files a & b modified commit 1").call();
		git.checkout().setName(TEST_BRANCH_NAME).call();
