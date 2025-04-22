		final IContext context2 = contextManager
				.getContext("org.eclipse.ui.tests.contexts.context2");
		assertTrue(
				"Context contributed via 'org.eclipse.ui.contexts' is not loaded properly.",
				context2.isDefined());
		assertEquals(
				"Context contributed via 'org.eclipse.ui.contexts' does not get its name.",
				"Test Context 2", context2.getName());
		assertEquals(
				"Context contributed via 'org.eclipse.ui.contexts' does not get its parent.",
				"org.eclipse.ui.tests.contexts.context1", context2
						.getParentId());
