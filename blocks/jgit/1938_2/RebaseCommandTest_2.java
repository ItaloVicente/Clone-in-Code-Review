	public void testDetachedHead() throws Exception {
		Git git = new Git(db);

		File theFile = writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit second = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()
		writeTrashFile("file1"
		checkFile(theFile
		git.add().addFilepattern("file1").call();
		RevCommit lastMasterChange = git.commit()
				.setMessage("change file1 in master").call();

		createBranch(second
		checkoutBranch("refs/heads/topic");
		checkFile(theFile

		assertTrue(new File(db.getWorkTree()
		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit topicCommit = git.commit()
				.setMessage("change file1 in topic").call();
		checkoutBranch("refs/heads/master");
		checkoutCommit(topicCommit);
		assertEquals(topicCommit.getId().getName()

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.OK
		checkFile(theFile
		assertEquals(lastMasterChange
				new RevWalk(db).parseCommit(db.resolve(Constants.HEAD))
						.getParent(0));

	}

