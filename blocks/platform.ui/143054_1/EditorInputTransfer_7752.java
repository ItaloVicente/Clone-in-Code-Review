		DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			int count = in.readInt();
			EditorInputData[] results = new EditorInputData[count];
			for (int i = 0; i < count; i++) {
				results[i] = readEditorInput(in);
			}
			return results;
		} catch (IOException e) {
			return null;
		} catch (WorkbenchException e) {
