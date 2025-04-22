		String editorId = selectedEditor.getId();
		settings.put(STORE_ID_DESCR, editorId);
		if (rememberEditorButton == null || !rememberEditorButton.getSelection()) {
			return;
		}
		EditorRegistry reg = (EditorRegistry) WorkbenchPlugin.getDefault().getEditorRegistry();
		boolean useFileName = fileNameButton.getSelection();
		updateFileMappings(reg, useFileName);
		if (useFileName) {
			reg.setDefaultEditor(fileName, editorId);
		} else {
			reg.setDefaultEditor("*." + getFileType(), editorId); //$NON-NLS-1$
		}
	}

	private void updateFileMappings(EditorRegistry reg, boolean useFileName) {
		IFileEditorMapping[] mappings = reg.getFileEditorMappings();
		boolean hasMapping = false;
		String fileType = getFileType();
		for (IFileEditorMapping mapping : mappings) {
			if (useFileName) {
				if (fileName.equals(mapping.getLabel())) {
					hasMapping = true;
					break;
				}
			} else {
				if (fileType.equals(mapping.getExtension())) {
					hasMapping = true;
					break;
				}

			}
		}
		if (hasMapping) {
			return;
		}
		FileEditorMapping mapping;
		if (useFileName) {
			mapping = new FileEditorMapping(fileName, null);
		} else {
			mapping = new FileEditorMapping(null, fileType);
		}
		List<IFileEditorMapping> newMappings = new ArrayList<IFileEditorMapping>();
		newMappings.addAll(Arrays.asList(mappings));
		newMappings.add(mapping);
		FileEditorMapping[] array = newMappings.toArray(new FileEditorMapping[newMappings.size()]);
		reg.setFileEditorMappings(array);
