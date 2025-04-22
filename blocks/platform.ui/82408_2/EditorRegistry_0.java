					lookupEditorFromTable(editorTable, editor);
					continue;
				}
				ImageDescriptor descriptor;
				if (editor.getProgram() == null) {
					String fileName = editor.getFileName();
					if (fileName == null) {
						String error = "Both editor program and path are null for descriptor id: "; //$NON-NLS-1$
						error += editor.getId() + " with name: " + editor.getLabel(); //$NON-NLS-1$
						WorkbenchPlugin.log(error, new IllegalStateException());
						continue;
