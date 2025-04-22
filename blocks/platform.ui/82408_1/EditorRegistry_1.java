					descriptor = new ProgramImageDescriptor(fileName, 0);
				} else {
					descriptor = new ExternalProgramImageDescriptor(editor.getProgram());
				}
				editor.setImageDescriptor(descriptor);
				editorTable.put(editor.getId(), editor);
