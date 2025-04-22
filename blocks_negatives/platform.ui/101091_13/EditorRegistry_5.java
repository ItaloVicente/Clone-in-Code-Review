			String name = childMemento.getString(IWorkbenchConstants.TAG_NAME);
            if (name == null) {
			}
			String extension = childMemento.getString(IWorkbenchConstants.TAG_EXTENSION);
			IMemento[] idMementos = childMemento.getChildren(IWorkbenchConstants.TAG_EDITOR);
            String[] editorIDs = new String[idMementos.length];
            for (int j = 0; j < idMementos.length; j++) {
				editorIDs[j] = idMementos[j].getString(IWorkbenchConstants.TAG_ID);
            }
			idMementos = childMemento.getChildren(IWorkbenchConstants.TAG_DELETED_EDITOR);
            String[] deletedEditorIDs = new String[idMementos.length];
            for (int j = 0; j < idMementos.length; j++) {
				deletedEditorIDs[j] = idMementos[j].getString(IWorkbenchConstants.TAG_ID);
            }
			String key = name;
			if (extension != null && extension.length() > 0) {
			}
			FileEditorMapping mapping = getMappingFor(key);
            if (mapping == null) {
                mapping = new FileEditorMapping(name, extension);
            }
			List<IEditorDescriptor> editors = new ArrayList<>();
            for (String editorID : editorIDs) {
                if (editorID != null) {
					IEditorDescriptor editor = editorTable.get(editorID);
                    if (editor != null) {
                        editors.add(editor);
                    }
                }
            }
			List<IEditorDescriptor> deletedEditors = new ArrayList<>();
            for (String deletedEditorID : deletedEditorIDs) {
                if (deletedEditorID != null) {
					IEditorDescriptor editor = editorTable.get(deletedEditorID);
                    if (editor != null) {
                        deletedEditors.add(editor);
                    }
                }
            }

			List<IEditorDescriptor> defaultEditors = new ArrayList<>();
