			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Deactivate:
				case SWT.FocusOut:
				case SWT.Dispose:
					if (!handlerActivations.isEmpty()) {
						service.deactivateHandlers(handlerActivations);
						handlerActivations.clear();
					}
					break;
				case SWT.Activate:
				case SWT.FocusIn:
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
