		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
		Reader reader = null;
		FileInputStream stream = null;
		try {
			String xmlString = store.getString(IPreferenceConstants.RESOURCES);
			if (xmlString == null || xmlString.length() == 0) {
				stream = new FileInputStream(
						workbenchStatePath.append(IWorkbenchConstants.RESOURCE_TYPE_FILE_NAME).toOSString());
				reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
			} else {
				reader = new StringReader(xmlString);
			}
			readResources(editorTable, reader);
		} catch (IOException e) {
			MessageDialog.openError((Shell) null, WorkbenchMessages.EditorRegistry_errorTitle,
					WorkbenchMessages.EditorRegistry_errorMessage);
			return false;
		} catch (WorkbenchException e) {
			ErrorDialog.openError((Shell) null, WorkbenchMessages.EditorRegistry_errorTitle,
					WorkbenchMessages.EditorRegistry_errorMessage, e.getStatus());
			return false;
		} finally {
			try {
				if (reader != null) {
