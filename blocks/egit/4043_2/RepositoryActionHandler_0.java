			IEditorInput editorInput = (IEditorInput) HandlerUtil
					.getVariable(event, ISources.ACTIVE_EDITOR_INPUT_NAME);
			IResource resource = ResourceUtil.getResource(editorInput);
			if (resource != null)
				return new StructuredSelection(resource);

			resource = ResourceUtil.getFile(editorInput);
