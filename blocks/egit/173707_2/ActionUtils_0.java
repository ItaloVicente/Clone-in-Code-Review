			public void handleEvent(Event event) {
				if (event.type == SWT.Deactivate
						|| event.type == SWT.FocusOut) {
					if (!handlerActivations.isEmpty()) {
						service.deactivateHandlers(handlerActivations);
						handlerActivations.clear();
					}
				} else if (event.type == SWT.Activate
						|| event.type == SWT.FocusIn) {
					if (!handlerActivations.isEmpty()) {
						return;
					}
					for (IAction action : actions) {
						if (action != null) {
							handlerActivations.add(service.activateHandler(
									action.getActionDefinitionId(),
									new ActionHandler(action), expression,
									false));
							if (action instanceof IUpdate) {
								((IUpdate) action).update();
							}
