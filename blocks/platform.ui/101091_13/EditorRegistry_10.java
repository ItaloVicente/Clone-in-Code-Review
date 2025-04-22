				List<IEditorDescriptor> deletedEditors = getEditorDescriptors(childMemento.getChildren(IWorkbenchConstants.TAG_DELETED_EDITOR), editorTable);

				List<IEditorDescriptor> defaultEditors = null;
				if (versionIs31) { // parse the new format
					defaultEditors = getEditorDescriptors(childMemento.getChildren(IWorkbenchConstants.TAG_DEFAULT_EDITOR), editorTable);
				} else { // guess at pre 3.1 format defaults
					defaultEditors = new ArrayList<>(
							(editors.isEmpty() ? 0 : 1) + mapping.getDeclaredDefaultEditors().length);
					if (!editors.isEmpty()) {
						IEditorDescriptor editor = editors.get(0);
						defaultEditors.add(editor);
					}
					defaultEditors.addAll(Arrays.asList(mapping.getDeclaredDefaultEditors()));
