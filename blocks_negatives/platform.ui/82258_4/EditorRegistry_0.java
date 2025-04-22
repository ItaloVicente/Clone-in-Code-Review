					IEditorDescriptor [] editorArray = contentTypeToEditorMappings.get(contentType);
					if (editorArray == null) {
						editorArray = new IEditorDescriptor[] {editor};
						contentTypeToEditorMappings.put(contentType, editorArray);
					}
					else {
						IEditorDescriptor [] newArray = new IEditorDescriptor[editorArray.length + 1];
							newArray[0] = editor;
							System.arraycopy(editorArray, 0, newArray, 1, editorArray.length);
						}
						else {
							newArray[editorArray.length] = editor;
							System.arraycopy(editorArray, 0, newArray, 0, editorArray.length);
						}
						contentTypeToEditorMappings.put(contentType, newArray);
					}
