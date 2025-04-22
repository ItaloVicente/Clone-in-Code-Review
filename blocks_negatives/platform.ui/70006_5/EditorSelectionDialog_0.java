		boolean wasExternal = settings.getBoolean(STORE_ID_INTERNAL_EXTERNAL);
		internalButton.setSelection(!wasExternal);
		externalButton.setSelection(wasExternal);
		String id = settings.get(STORE_ID_DESCR);
		if (id != null) {
			IEditorDescriptor[] editors;
			if (wasExternal) {
				editors = getExternalEditors();
			} else {
				editors = getInternalEditors();
			}
			for (IEditorDescriptor desc : editors) {
				if (id.equals(desc.getId())) {
					selectedEditor = desc;
