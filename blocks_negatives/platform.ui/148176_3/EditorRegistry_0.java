					defaultEditors = getEditorDescriptors(
							childMemento.getChildren(IWorkbenchConstants.TAG_DEFAULT_EDITOR), editorTable);
					defaultEditors = new ArrayList<>(
							(editors.isEmpty() ? 0 : 1) + mapping.getDeclaredDefaultEditors().length);
					if (!editors.isEmpty()) {
						IEditorDescriptor editor = editors.get(0);
						defaultEditors.add(editor);
					}
					defaultEditors.addAll(Arrays.asList(mapping.getDeclaredDefaultEditors()));
				}
