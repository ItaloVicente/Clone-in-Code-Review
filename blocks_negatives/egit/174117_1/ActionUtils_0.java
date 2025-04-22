		Collection<IHandlerActivation> handlerActivations = new ArrayList<>();
		control.addDisposeListener(event -> {
			if (!handlerActivations.isEmpty()) {
				service.deactivateHandlers(handlerActivations);
				handlerActivations.clear();
			}
		});
		final ActiveShellExpression expression = new ActiveShellExpression(
