	@Test
	public void testResetToNonexistingHEAD() throws JGitInternalException
			AmbiguousObjectException

		git = new Git(db);
		writeTrashFile("f"

		try {
			git.reset().setRef(Constants.HEAD).call();
			fail("Expected JGitInternalException didn't occur");
		} catch (JGitInternalException e) {
		}
	}

