		for (IEditorReference part : workbenchWindow.getActivePage().getEditorReferences()) {
			try {
				if (part.getEditorInput() == PreferencesEditorInput.INSTANCE) {
					part.getPage().bringToTop(part.getPart(true));
					part.getPart(true).setFocus();
					return;
				}
			} catch (PartInitException e) {
				WorkbenchPlugin.log(e);
			}
		}
		if (IPreferenceConstants.PREFERENCE_FACADE_MODE
				.valueOf(WorkbenchPlugin.getDefault().getPreferenceStore().getString(
						IPreferenceConstants.PREFERENCE_FACADE)) == IPreferenceConstants.PREFERENCE_FACADE_MODE.EDITOR) {
			try {
				workbenchWindow.getActivePage().openEditor(new NullEditorInput(), PreferencesEditor.EDITOR_ID);
				return;
			} catch (PartInitException e) {
				WorkbenchPlugin.log(e);
			}
		}

