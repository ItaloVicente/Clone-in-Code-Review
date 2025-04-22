		assertEquals(RepositoryState.SAFE

		assertFalse(new File(db.getDirectory()
	}

	public void testAbortOnConflictFileCreationAndDeletion() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		File file2 = writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		File file3 = writeTrashFile("file3"
		git.add().addFilepattern("file3").call();

		RevCommit firstInMaster = git.commit()
				.setMessage("Add file 1

		File file4 = writeTrashFile("file4"
		git.add().addFilepattern("file4").call();

		deleteTrashFile("file2");
		git.add().setUpdate(true).addFilepattern("file2").call();
		writeTrashFile("folder6/file1"
		git.add().addFilepattern("folder6/file1").call();

		git.commit()
				.setMessage(
						"Add file 4 and folder folder6
				.call();

		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");

		deleteTrashFile("file3");
		git.add().setUpdate(true).addFilepattern("file3").call();
		File file5 = writeTrashFile("file5"
		git.add().addFilepattern("file5").call();
		git.commit().setMessage("Delete file3 and add file5 in topic").call();

		writeTrashFile("folder6"
		git.add().addFilepattern("folder6").call();
		File file7 = writeTrashFile("file7"
		git.add().addFilepattern("file7").call();

		deleteTrashFile("file5");
		git.add().setUpdate(true).addFilepattern("file5").call();
		RevCommit conflicting = git.commit()
				.setMessage("Delete file5
				.call();

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED
		assertEquals(conflicting

		assertEquals(RepositoryState.REBASING_MERGE
		assertTrue(new File(db.getDirectory()
		assertEquals(1

		assertFalse(file2.exists());
		assertFalse(file3.exists());
		assertTrue(file4.exists());
		assertFalse(file5.exists());
		assertTrue(file7.exists());

		res = git.rebase().setOperation(Operation.ABORT).call();
		assertEquals(res.getStatus()
		assertEquals("refs/heads/topic"
		RevWalk rw = new RevWalk(db);
		assertEquals(conflicting
				rw.parseCommit(db.resolve(Constants.HEAD)));
		assertEquals(RepositoryState.SAFE

		assertFalse(new File(db.getDirectory()

		assertTrue(file2.exists());
		assertFalse(file3.exists());
		assertFalse(file4.exists());
		assertFalse(file5.exists());
		assertTrue(file7.exists());

