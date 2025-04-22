	private void initializeSuggestion() {
		IEditorRegistry editorRegistry = PlatformUI.getWorkbench().getEditorRegistry();
		IEditorDescriptor suggestion = editorRegistry.getDefaultEditor(fileName);
		if (suggestion != null && suggestion.isInternal()) {
			selectedEditor = suggestion;
		} else {
			selectedEditor = findBestExternalEditor();
		}
		boolean enableInternalList = selectedEditor == null || selectedEditor.isInternal();
		internalButton.setSelection(enableInternalList);
		externalButton.setSelection(!enableInternalList);
	}

	private IEditorDescriptor findBestExternalEditor() {
		String extension = getFileExtension(fileName);
		Program program = Program.findProgram(extension);
		if (program != null) {
			for (IEditorDescriptor descriptor : getExternalEditors()) {
				if (descriptor instanceof EditorDescriptor
						&& program.equals(((EditorDescriptor) descriptor).getProgram())) {
					return descriptor;
				}
			}
		}
		return null;
	}

