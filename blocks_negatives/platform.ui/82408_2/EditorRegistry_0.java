					IEditorDescriptor validEditorDescritor = mapIDtoEditor.get(editor.getId());
                    if (validEditorDescritor != null) {
                        editorTable.put(validEditorDescritor.getId(),
                                validEditorDescritor);
                    }
                    ImageDescriptor descriptor;
                    if (editor.getProgram() == null) {
						descriptor = new ProgramImageDescriptor(editor
                                .getFileName(), 0);
					} else {
						descriptor = new ExternalProgramImageDescriptor(editor
                                .getProgram());
