		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareRevert(git);

			RevertCommand revert = git.revert();
			RevCommit newHead = revert.include(sideCommit.getId())
					.setOurCommitName("custom name").call();
			assertNull(newHead);
			MergeResult result = revert.getFailingResult();
			assertEquals(MergeStatus.CONFLICTING

			String expected = "<<<<<<< custom name\na(latest)\n=======\na\n>>>>>>> ca96c31 second master\n";
			checkFile(new File(db.getWorkTree()
		}
