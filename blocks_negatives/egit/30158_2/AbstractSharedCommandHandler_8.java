	/**
	 * Return the input of the active editor.
	 * <strong>Note:</strong> Copied from org.eclipse.ui.handlers.HandlerUtil.getActiveEditorInput(ExecutionEvent) for compatibility reasons.
	 *
	 * @param event
	 *            The execution event that contains the application context
	 * @return the input of the active editor, or <code>null</code>.
	 */
	private static IEditorInput getActiveEditorInput(ExecutionEvent event) {
		Object o = HandlerUtil.getVariable(event, ISources.ACTIVE_EDITOR_INPUT_NAME);
		if (o instanceof IEditorInput)
			return (IEditorInput) o;
		return null;
	}

