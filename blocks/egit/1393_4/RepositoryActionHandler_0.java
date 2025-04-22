		if (selection instanceof TextSelection) {
			IEditorInput input = (IEditorInput) HandlerUtil.getVariable(event,
					ISources.ACTIVE_EDITOR_INPUT_NAME);
			if (input == null)
				return StructuredSelection.EMPTY;
			IResource resource = ResourceUtil.getResource(input);
			if (resource != null)
				return new StructuredSelection(resource);
		}
