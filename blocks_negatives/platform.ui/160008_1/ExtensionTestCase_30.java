		final IContext context1 = contextManager
				.getContext("org.eclipse.ui.tests.commands.scope1");
		assertTrue(
				"Context contributed via 'org.eclipse.ui.commands' is not loaded properly.",
				context1.isDefined());
		assertEquals(
				"Context contributed via 'org.eclipse.ui.commands' does not get its name.",
				"Test Scope 1", context1.getName());
