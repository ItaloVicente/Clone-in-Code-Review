		this.linkingEnabled = enabled;

		settings.put(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR, enabled);

		if (enabled) {
			IEditorPart editor = getSite().getPage().getActiveEditor();
			if (editor != null) {
				editorActivated(editor);
			}
		}
		openAndLinkWithEditorHelper.setLinkWithEditor(enabled);
	}

	@Deprecated
