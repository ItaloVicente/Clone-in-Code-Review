		assertTrue(handlers.size() > 0);
		List<MCommand> commands = application.getCommands();
		MCommand expected = commands.get(0);


		assertEquals(2, expected.getParameters().size());
