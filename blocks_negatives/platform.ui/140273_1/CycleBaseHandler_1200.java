		final IBindingService bindingService = window
				.getWorkbench().getService(IBindingService.class);
		forwardTriggerSequences = bindingService
				.getActiveBindingsFor(commandForward);
		backwardTriggerSequences = bindingService
				.getActiveBindingsFor(commandBackward);
