				if (quickFixActionHandler != null)
					quickFixHandlerActivation = getHandlerService().activateHandler(
							quickFixActionHandler.getAction().getActionDefinitionId(),
							quickFixActionHandler,
							new ActiveShellExpression(getParent().getShell()));
				if (contentAssistActionHandler != null)
					contentAssistHandlerActivation = getHandlerService().activateHandler(
							contentAssistActionHandler.getAction().getActionDefinitionId(),
							contentAssistActionHandler,
							new ActiveShellExpression(getParent().getShell()));
