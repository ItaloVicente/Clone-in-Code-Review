		final IContext context2 = contextManager
				.getContext("org.eclipse.ui.tests.acceleratorScopes.test2");
		assertTrue(
				"Context contributed via 'org.eclipse.ui.acceleratorScopes' is not loaded properly.",
				context2.isDefined());
		assertEquals(
				"Context contributed via 'org.eclipse.ui.acceleratorScopes' does not get its name.",
				"Test Accelerator Scope 2", context2.getName());
		assertEquals(
				"Context contributed via 'org.eclipse.ui.acceleratorScopes' does not get its parent.",
				"org.eclipse.ui.tests.acceleratorScopes.test1", context2
						.getParentId());
	}
