	void addContentTypeBinding(IContentType contentType, IEditorDescriptor editor, boolean bDefault) {
		IEditorDescriptor [] editorArray = contentTypeToEditorMappings.get(contentType);
		if (editorArray == null) {
			editorArray = new IEditorDescriptor[] {editor};
			contentTypeToEditorMappings.put(contentType, editorArray);
		}
		else {
			IEditorDescriptor [] newArray = new IEditorDescriptor[editorArray.length + 1];
			if (bDefault) { // default editors go to the front of the line
				newArray[0] = editor;
				System.arraycopy(editorArray, 0, newArray, 1, editorArray.length);
			}
			else {
				newArray[editorArray.length] = editor;
				System.arraycopy(editorArray, 0, newArray, 0, editorArray.length);
			}
			contentTypeToEditorMappings.put(contentType, newArray);
		}
	}

