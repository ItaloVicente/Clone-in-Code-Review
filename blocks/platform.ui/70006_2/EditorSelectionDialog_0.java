		} else if (!showInternal) {
			int index = fileName.lastIndexOf('.');
			if (index != -1) {
				String extension = fileName.substring(index);
				Program program = Program.findProgram(extension);
				if (program != null) {
					for (IEditorDescriptor descriptor : externalEditors) {
						if (descriptor instanceof EditorDescriptor
								&& program.equals(((EditorDescriptor) descriptor).getProgram())) {
							editorTableViewer.setSelection(new StructuredSelection(descriptor), true);
						}
					}
				}
			}
		}

		if (editorTableViewer.getSelection().isEmpty()) {
