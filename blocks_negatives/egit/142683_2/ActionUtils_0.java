				for (final IAction action : actions) {
					handlerActivations.add(service.activateHandler(
							action.getActionDefinitionId(),
							new ActionHandler(action), expression, false));
					if (action instanceof IUpdate) {
						((IUpdate) action).update();
