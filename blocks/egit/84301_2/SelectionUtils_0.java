		Object editor = context.getVariable(ISources.ACTIVE_EDITOR_NAME);
		if (editor instanceof MultiPageEditorPart) {
			Object nestedEditor = ((MultiPageEditorPart) editor)
					.getSelectedPage();
			if (nestedEditor instanceof IEditorPart) {
				object = ((IEditorPart) nestedEditor).getEditorInput();
			}
		}
		if (!(object instanceof IEditorInput)
				&& (editor instanceof IEditorPart)) {
			object = ((IEditorPart) editor).getEditorInput();
