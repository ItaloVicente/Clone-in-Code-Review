	@Test
	public void testHelpAfterDelete() throws Exception {
		String err = toString(executeUnchecked("git branch -d"));
		String help = toString(executeUnchecked("git branch -h"));
		String errAndHelp = toString(executeUnchecked("git branch -d -h"));
		assertEquals(CLIText.fatalError(CLIText.get().branchNameRequired)
		assertEquals(toString(err
	}

