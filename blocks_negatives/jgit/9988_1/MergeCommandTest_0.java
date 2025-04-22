	@Test
	public void testMergeCannotDoRequiredFastForward() throws Exception {
		Git git = new Git(db);

		RevCommit first = git.commit().setMessage("first").call();
		createBranch(first, "refs/heads/side");

		writeTrashFile("a", "a");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("second").call();

		checkoutBranch("refs/heads/side");
		writeTrashFile("b", "b");
		git.add().addFilepattern("b").call();
		git.commit().setMessage("third").call();

		MergeResult result = git.merge().include(db.getRef(Constants.MASTER))
				.setFastForward(FastForwardMode.NO_FF)
				.call();
		assertEquals(MergeStatus.ABORTED, result.getMergeStatus());
	}

