				if (largeFileEditorId.isPresent()) {
					editorId = largeFileEditorId.get();
					desc = editorRegistry.findEditor(editorId);
					if (desc instanceof EditorDescriptor && desc.isOpenExternal()) {
						openExternalEditor((EditorDescriptor) desc, input);
						return null;
					}
				}
