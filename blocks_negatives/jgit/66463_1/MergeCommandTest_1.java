		Git git = new Git(db);

		writeTrashFile("file1", "file1");
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("initial commit").call();
		createBranch(first, "refs/heads/branch1");

		writeTrashFile("file2", "file2");
		git.add().addFilepattern("file2").call();
		RevCommit second = git.commit().setMessage("second commit").call();

		writeTrashFile("file3", "file3");
		git.add().addFilepattern("file3").call();
		git.commit().setMessage("third commit").call();

		checkoutBranch("refs/heads/branch1");
		assertFalse(new File(db.getWorkTree(), "file2").exists());
		assertFalse(new File(db.getWorkTree(), "file3").exists());

		MergeCommand merge = git.merge();
		merge.include(second.getId());
		merge.include(db.exactRef(R_HEADS + MASTER));
		try {
			merge.call();
			fail("Expected exception not thrown when merging multiple heads");
		} catch (InvalidMergeHeadsException e) {
