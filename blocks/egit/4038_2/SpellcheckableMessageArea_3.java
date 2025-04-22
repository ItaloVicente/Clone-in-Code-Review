		return addHandler(handler);
	}

	private IHandlerActivation installContentAssistActionHandler() {
		ActionHandler handler = createContentAssistActionHandler(sourceViewer);
		return addHandler(handler);
	}

	private IHandlerActivation addHandler(ActionHandler handler) {
