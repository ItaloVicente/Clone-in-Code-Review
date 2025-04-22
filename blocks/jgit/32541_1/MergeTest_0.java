
	@Test
	public void testMergeWithUserMessage() throws Exception {
		git.branchCreate().setName("side").call();
		writeTrashFile("master"
		git.add().addFilepattern("master").call();
		git.commit().setMessage("master commit").call();
		git.checkout().setName("side").call();
		writeTrashFile("side"
		git.add().addFilepattern("side").call();
		git.commit().setMessage("side commit").call();

		assertEquals("Merge made by the '" + MergeStrategy.RECURSIVE.getName()
				+ "' strategy."
				execute("git merge master -m \"user message\"")[0]);

		Iterator<RevCommit> it = git.log().call().iterator();
		RevCommit newHead = it.next();
		assertEquals("user message"
	}
