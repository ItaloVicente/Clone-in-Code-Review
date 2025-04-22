		final IContext context1 = contextManager
				.getContext("org.eclipse.ui.tests.acceleratorScopes.test1");
		assertTrue(
				"Context contributed via 'org.eclipse.ui.acceleratorScopes' is not loaded properly.",
				context1.isDefined());
		assertEquals(
				"Context contributed via 'org.eclipse.ui.acceleratorScopes' does not get its name.",
				"Test Accelerator Scope 1", context1.getName());
