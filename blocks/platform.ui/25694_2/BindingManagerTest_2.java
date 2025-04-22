		assertEquals("There should be four bindings left", 4,
				bindingManager.getBindings().length);
		assertNotNull("There should be four active bindings",
				bindingManager.getActiveBindingsFor(binding1
						.getParameterizedCommand()));
		assertNotNull("There should be four active bindings",
				bindingManager.getActiveBindingsFor(binding2
						.getParameterizedCommand()));
		assertNotNull("There should be four active bindings",
				bindingManager.getActiveBindingsFor(binding3
						.getParameterizedCommand()));
		assertNotNull("There should be four active bindings",
				bindingManager.getActiveBindingsFor(binding4
						.getParameterizedCommand()));
