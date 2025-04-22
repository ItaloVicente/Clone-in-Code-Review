			if (largeFileEditorId.isPresent()) {
				editorId = largeFileEditorId.get();
				IEditorDescriptor largeFileDescriptor = editorRegistry.findEditor(editorId);
				if (largeFileDescriptor instanceof EditorDescriptor && largeFileDescriptor.isOpenExternal()) {
					openExternalEditor((EditorDescriptor) largeFileDescriptor, input);
					return null;
				}
			}
