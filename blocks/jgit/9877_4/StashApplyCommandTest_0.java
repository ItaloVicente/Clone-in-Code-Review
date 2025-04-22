		} catch (StashApplyFailureException e) {
 		}
		assertEquals("content3"
	}

	@Test
	public void stashedContentMerge() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();

		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content\nhead change\nmore content\n"
				read(committedFile));
		assertTrue(git.status().call().isClean());

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("committed change").call();

		try {
			git.stashApply().call();
			fail("Expected conflict");
		} catch (StashApplyFailureException e) {
