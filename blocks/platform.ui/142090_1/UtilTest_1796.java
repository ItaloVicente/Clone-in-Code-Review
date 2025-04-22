		assertTrue(bindings.size() == 1);
		IActivityPatternBinding binding =
			bindings.iterator().next();
		assertTrue(binding.isEqualityPattern());

		final String IDENTIFIER = "org.eclipse.ui.tests.activity{No{Reg(Exp[^d]";
		IIdentifier identifier = manager.getIdentifier(IDENTIFIER);
