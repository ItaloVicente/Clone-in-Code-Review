			}
		}
		for (Entry<IContentType, LinkedHashSet<IEditorDescriptor>> mapping : contentTypeToEditorMappingsFromUser
				.entrySet()) {
            IMemento editorMemento = memento.createChild(IWorkbenchConstants.TAG_INFO);
			editorMemento.putString(IWorkbenchConstants.TAG_CONTENT_TYPE, mapping.getKey().getId());
			for (IEditorDescriptor editor : mapping.getValue()) {
				if (editor == null) {
					continue;
				}
				editors.add(editor);
				IMemento idMemento = editorMemento.createChild(IWorkbenchConstants.TAG_EDITOR);
				idMemento.putString(IWorkbenchConstants.TAG_ID, editor.getId());
