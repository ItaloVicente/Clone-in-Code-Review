			if (editor == null) {
				WorkbenchPlugin.log("Error setting default editor. Could not find editor: '" + editorId + "'."); //$NON-NLS-1$ //$NON-NLS-2$
				continue;
			}
			mapping.setDefaultEditor(editor);
		}
	}
