		IEditorPart activeNestedEditor = null;
		if (part instanceof MultiPageEditorPart) {
			activeNestedEditor = ((MultiPageEditorPart) part).getActiveEditor();
		}
		setActivePage(activeNestedEditor);
	}
