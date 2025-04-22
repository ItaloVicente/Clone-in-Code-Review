				for (IAction action : actions) {
					if (action != null) {
						handlerActivations.add(service.activateHandler(
								action.getActionDefinitionId(),
								new ActionHandler(action), expression, false));
						if (action instanceof IUpdate) {
							((IUpdate) action).update();
						}
