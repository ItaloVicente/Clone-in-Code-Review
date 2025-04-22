		while (extEditors.hasMoreTokens()) {
			String extEditor = extEditors.nextToken().trim();
			int index = extEditor.indexOf(':');
			if (extEditor.length() < 3 || index <= 0 || index >= (extEditor.length() - 1)) {
				WorkbenchPlugin.log("Error setting default editor. Could not parse '" + extEditor //$NON-NLS-1$
						+ "'. Default editors should be specified as '*.ext1:editorId1;*.ext2:editorId2'"); //$NON-NLS-1$
				return;
			}
			String ext = extEditor.substring(0, index).trim();
			String editorId = extEditor.substring(index + 1).trim();
			FileEditorMapping mapping = getMappingFor(ext);
			if (mapping == null) {
				WorkbenchPlugin.log("Error setting default editor. Could not find mapping for '" + ext + "'."); //$NON-NLS-1$ //$NON-NLS-2$
				continue;
			}
