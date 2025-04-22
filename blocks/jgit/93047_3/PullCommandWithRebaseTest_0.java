	@Test
	public void testPullFastForwardDetachedHead() throws Exception {
		Repository repository = source.getRepository();
		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("2nd commit").call();

		try (RevWalk revWalk = new RevWalk(repository)) {
			String initialBranch = repository.getBranch();
			Ref initialRef = repository.findRef(Constants.HEAD);
			RevCommit initialCommit = revWalk
					.parseCommit(initialRef.getObjectId());
			assertEquals("this test need linear history"
					initialCommit.getParentCount());
			source.checkout().setName(initialCommit.getParent(0).getName())
					.call();
			assertFalse("expected detached HEAD"
					repository.getFullBranch().startsWith(Constants.R_HEADS));

			File otherFile = new File(sourceFile.getParentFile()
					System.currentTimeMillis() + ".tst");
			writeToFile(otherFile
			source.add().addFilepattern(otherFile.getName()).call();
			RevCommit newCommit = source.commit().setMessage("other 2nd commit")
					.call();

			source.pull().setRebase(true).setRemote(".")
					.setRemoteBranchName(initialBranch)
					.call();

			assertEquals(RepositoryState.SAFE
					source.getRepository().getRepositoryState());
			Ref head = source.getRepository().findRef(Constants.HEAD);
			RevCommit headCommit = revWalk.parseCommit(head.getObjectId());

			assertEquals(1
			assertEquals(initialCommit

			assertFileContentsEqual(sourceFile
			assertFileContentsEqual(otherFile
			assertEquals(newCommit.getShortMessage()
					headCommit.getShortMessage());
		}
	}

