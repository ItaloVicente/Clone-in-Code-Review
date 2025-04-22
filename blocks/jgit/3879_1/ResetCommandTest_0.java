	@Test
	public void testResetToNonexistingHEAD() throws JGitInternalException
			AmbiguousObjectException

		git = new Git(db);
		writeTrashFile("f"

		try {
			git.reset().setRef(Constants.HEAD).call();
			fail("Excpected JGitInternalException didn't occured");
		} catch (JGitInternalException e) {
		}
	}

