		try {
			helpContextId = commandService.getHelpContextId(COMMAND_ID);
			fail("A NotDefinedException should have been thrown");
		} catch (final NotDefinedException e) {
		}

