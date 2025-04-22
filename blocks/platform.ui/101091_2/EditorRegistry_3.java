            List<IEditorDescriptor> editors = getEditorDescriptors(childMemento.getChildren(IWorkbenchConstants.TAG_EDITOR), editorTable);
			String contentTypeId = childMemento.getString(IWorkbenchConstants.TAG_CONTENT_TYPE);
			if (contentTypeId != null) {
				IContentType contentType = Platform.getContentTypeManager().getContentType(contentTypeId);
				if (contentType != null) {
					contentTypeToEditorMappingsFromUser.put(contentType, new LinkedHashSet<>(editors));
				}
			} else {
				String name = childMemento.getString(IWorkbenchConstants.TAG_NAME);
				if (name == null) {
					name = "*"; //$NON-NLS-1$
				}
				String extension = childMemento.getString(IWorkbenchConstants.TAG_EXTENSION);
				String key = name;
				if (extension != null && extension.length() > 0) {
					key = key + "." + extension; //$NON-NLS-1$
				}
				FileEditorMapping mapping = getMappingFor(key);
				if (mapping == null) {
					mapping = new FileEditorMapping(name, extension);
				}
