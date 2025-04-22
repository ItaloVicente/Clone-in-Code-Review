		Binding binding1 = new KeyBinding(conflict, parameterizedCommand1,
				"na", "na", null, null, null, Binding.SYSTEM);
		Binding binding2 = new KeyBinding(conflict, parameterizedCommand2,
				"na", "na", null, null, null, Binding.SYSTEM);
		Binding binding3 = new KeyBinding(noConflict, parameterizedCommand3,
				"na", "na", null, null, null, Binding.SYSTEM);
		final Binding[] bindings = new Binding[] { binding1, binding2, binding3 };
