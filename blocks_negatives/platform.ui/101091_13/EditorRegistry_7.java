				for (String defaultEditorId : defaultEditorIds) {
					if (defaultEditorId != null) {
						IEditorDescriptor editor = editorTable.get(defaultEditorId);
						if (editor != null) {
							defaultEditors.add(editor);
						}
