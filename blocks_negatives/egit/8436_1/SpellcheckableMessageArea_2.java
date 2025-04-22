	private IHandlerActivation installQuickFixActionHandler() {
		ActionHandler handler = createQuickFixActionHandler(sourceViewer);
		return addHandler(handler);
	}

	private IHandlerActivation installContentAssistActionHandler() {
		ActionHandler handler = createContentAssistActionHandler(sourceViewer);
		return addHandler(handler);
	}

	private IHandlerActivation addHandler(ActionHandler handler) {
		ActiveShellExpression expression = new ActiveShellExpression(
				sourceViewer.getTextWidget().getShell());
		return getHandlerService().activateHandler(
				handler.getAction().getActionDefinitionId(), handler,
				expression);
	}

