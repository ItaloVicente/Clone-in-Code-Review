	private static IEditorInput getActiveEditorInput(ExecutionEvent event) {
		Object o = HandlerUtil.getVariable(event, ISources.ACTIVE_EDITOR_INPUT_NAME);
		if (o instanceof IEditorInput)
			return (IEditorInput) o;
		return null;
	}

