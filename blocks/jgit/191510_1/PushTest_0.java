
	@Test
	public void testMergeDotGitAttributes() throws Exception {
		String branch = "new-branch";
		git.branchCreate().setName(branch).call();

		git.checkout().setName(Constants.MASTER).setForced(true).call();
		JGitTestUtil.writeTrashFile(localDb.getRepository()
				"blob");
		git.add().addFilepattern("a.blob").call();
		git.commit().setMessage("add blob").call();

		git.checkout().setName(branch).call();
		JGitTestUtil.writeTrashFile(localDb.getRepository()
				"text");
		git.add().addFilepattern("b.txt").call();
		git.commit().setMessage("add txt").call();

		git.checkout().setName(Constants.MASTER).setForced(true).call();
		JGitTestUtil.writeTrashFile(localDb.getRepository()
				"*.blob filter=lfs diff=lfs merge=lfs -text ");
		git.add().addFilepattern(Constants.DOT_GIT_ATTRIBUTES).call();
		git.commit().setMessage("modify .gitattributes").call();

		git.checkout().setName(branch).setForced(true).call();
		MergeResult result = git.merge()
				.include(git.getRepository().findRef(Constants.MASTER))
				.setCommit(false)
				.setMessage("Merged 2 branches")
				.setFastForward(MergeCommand.FastForwardMode.NO_FF)
				.call();
		assertTrue(result.getMergeStatus().isSuccessful());
	}
