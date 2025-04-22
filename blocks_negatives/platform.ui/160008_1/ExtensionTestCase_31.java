		final IContext context2 = contextManager
				.getContext("org.eclipse.ui.tests.commands.scope2");
		assertTrue(
				"Context contributed via 'org.eclipse.ui.commands' is not loaded properly.",
				context2.isDefined());
		assertEquals(
				"Context contributed via 'org.eclipse.ui.commands' does not get its name.",
				"Test Scope 2", context2.getName());
		assertEquals(
				"Context contributed via 'org.eclipse.ui.commands' does not get its parent.",
