	private static void handlerDeactivation(IHandlerService service,
			Collection<IHandlerActivation> handlerActivations) {
		if (!handlerActivations.isEmpty()) {
			service.deactivateHandlers(handlerActivations);
			handlerActivations.clear();
		}
	}

	private static void handlerActivation(Collection<? extends IAction> actions,
			IHandlerService service,
			Collection<IHandlerActivation> handlerActivations,
			final ActiveShellExpression expression) {
		if (!handlerActivations.isEmpty()) {
			return;
		}
		for (IAction action : actions) {
			if (action != null) {
				handlerActivations.add(
						service.activateHandler(action.getActionDefinitionId(),
								new ActionHandler(action), expression, false));
				if (action instanceof IUpdate) {
					((IUpdate) action).update();
				}
			}
		}
	}

