		doTestCherryPick(false);
	}

	@Test
	public void testCherryPickNoCommit() throws IOException
			JGitInternalException
		doTestCherryPick(true);
	}

	private void doTestCherryPick(boolean noCommit) throws IOException
			JGitInternalException
			GitAPIException {
