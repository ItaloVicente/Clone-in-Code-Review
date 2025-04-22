				if (!editors.isEmpty()) {
					IEditorDescriptor editor = editors.get(0);
					defaultEditors.add(editor);
				}
				defaultEditors.addAll(Arrays.asList(mapping.getDeclaredDefaultEditors()));
            }

			for (IEditorDescriptor descriptor : mapping.getEditors()) {
				if (descriptor != null && !contains(editors, descriptor) && !deletedEditors.contains(descriptor)) {
					editors.add(descriptor);
                }
            }
            mapping.setEditorsList(editors);
            mapping.setDeletedEditorsList(deletedEditors);
            mapping.setDefaultEditors(defaultEditors);
            typeEditorMappings.put(mappingKeyFor(mapping), mapping);
        }
