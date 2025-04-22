		try (Git git = new Git(db)) {
			RevCommit first = git.commit().setMessage("first").call();
			createBranch(first

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("second").call();

			checkoutBranch("refs/heads/side");
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			RevCommit thirdCommit = git.commit().setMessage("third").call();

			MergeResult result = git.merge().setStrategy(mergeStrategy)
					.setCommit(false)
					.include(db.exactRef(R_HEADS + MASTER)).call();
			assertEquals(MergeStatus.MERGED_NOT_COMMITTED
			assertEquals(db.exactRef(Constants.HEAD).getTarget().getObjectId()
					thirdCommit.getId());
		}
