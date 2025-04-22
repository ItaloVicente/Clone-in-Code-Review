		if (selection instanceof TextSelection) {
			IEditorInput input = (IEditorInput) ctx
					.getVariable(ISources.ACTIVE_EDITOR_INPUT_NAME);
			if (input == null)
				return StructuredSelection.EMPTY;
			IResource resource = ResourceUtil.getResource(input);
			if (resource != null)
				return new StructuredSelection(resource);
		}
