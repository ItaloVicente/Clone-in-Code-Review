	@Test(expected = NoWorkTreeException.class)
	public void baseRepositoryShouldFail() throws IOException {
		db = createBareRepository();
		SubmoduleInitCommand command = new SubmoduleInitCommand(db);
		command.call();
	}

