		if (fileName != null && newSelection == null) {
			if (!showInternal) {
				newSelection = findBestExternalEditor();
			} else {
				newSelection = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(fileName);
			}
		}
