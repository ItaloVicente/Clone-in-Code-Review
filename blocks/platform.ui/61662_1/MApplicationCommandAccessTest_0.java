	@Test
	public void testFindCorrectCommandDirectAccess() {
		final MApplication application = createAppWithCommands();

		MCommand command = application.getCommand(String.valueOf(NUMBER_OF_COMMANDS - 1));
		assertNotNull(command);
		MCommand commandShouldBeNull = application.getCommand("invalid");
		assertNull(commandShouldBeNull);
	}

	@Test
	public void testFindNullAfterCommandRemoval() {
		final MApplication application = createAppWithCommands();

		MCommand command = application.getCommand(String.valueOf(NUMBER_OF_COMMANDS - 1));
		assertNotNull(command);
		application.getCommands().remove(command);
		MCommand commandShouldBeNull = application.getCommand(String.valueOf(NUMBER_OF_COMMANDS - 1));
		assertNull(commandShouldBeNull);
	}

	@Test
	public void testCommandAfterAddingIt() {
		final MApplication application = createAppWithCommands();
		EModelService modelService = applicationContext.get(EModelService.class);

		MCommand commandShouldBeNull = application.getCommand("neucommand");
		assertNull(commandShouldBeNull);
		MCommand mCommand = modelService.createModelElement(MCommand.class);
		mCommand.setElementId("neucommand");
		application.getCommands().add(mCommand);

		MCommand neuCommnad = application.getCommand("neucommand");
		assertNotNull(neuCommnad);
	}

