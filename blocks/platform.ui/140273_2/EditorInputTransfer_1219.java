
	}

	private EditorInputData readEditorInput(DataInputStream dataIn) throws IOException, WorkbenchException {

		String editorId = dataIn.readUTF();
		String factoryId = dataIn.readUTF();
		String xmlString = dataIn.readUTF();

		if (xmlString == null || xmlString.length() == 0) {
