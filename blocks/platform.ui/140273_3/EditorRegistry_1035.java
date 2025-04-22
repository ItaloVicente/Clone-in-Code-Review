		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
		Reader reader = null;
		FileInputStream stream = null;
		try {
			String xmlString = store.getString(IPreferenceConstants.EDITORS);
			if (xmlString == null || xmlString.length() == 0) {
				stream = new FileInputStream(
						workbenchStatePath.append(IWorkbenchConstants.EDITOR_FILE_NAME).toOSString());
				reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
			} else {
				reader = new StringReader(xmlString);
			}
			XMLMemento memento = XMLMemento.createReadRoot(reader);
