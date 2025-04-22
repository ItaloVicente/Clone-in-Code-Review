		if (selection instanceof TextSelection) {
			try {
				IEditorInput input = HandlerUtil.getActiveEditorChecked(event)
						.getEditorInput();
				if (input == null)
					return StructuredSelection.EMPTY;
				IResource resource = ResourceUtil.getResource(input);
				if (resource != null)
					return new StructuredSelection(resource);
			} catch (ExecutionException e) {
			}
		}
