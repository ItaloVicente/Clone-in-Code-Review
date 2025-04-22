		Object selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection == null)
			selection = HandlerUtil.getCurrentSelectionChecked(event);
		if (selection instanceof TextSelection) {
			IEditorInput editorInput = (IEditorInput) HandlerUtil.getVariable(
					event, ISources.ACTIVE_EDITOR_INPUT_NAME);
			IResource resource = ResourceUtil.getResource(editorInput);
			if (resource != null)
				return new StructuredSelection(resource);

			resource = ResourceUtil.getFile(editorInput);
			if (resource != null)
				return new StructuredSelection(resource);
		}
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
