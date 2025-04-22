		assertNotNull("There should be three active bindings", bindingManager
				.getActiveBindingsFor(binding1.getParameterizedCommand()));
		assertNotNull("There should be three active bindings", bindingManager
				.getActiveBindingsFor(binding2.getParameterizedCommand()));
		assertNotNull("There should be three active bindings", bindingManager
				.getActiveBindingsFor(binding4.getParameterizedCommand()));
