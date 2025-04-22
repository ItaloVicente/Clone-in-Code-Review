	public boolean isUserAssociation(IContentType contentType, IEditorDescriptor editor) {
		return this.contentTypeToEditorMappingsFromUser.containsKey(contentType)
				&& this.contentTypeToEditorMappingsFromUser.get(contentType).contains(editor);
	}

	public void removeUserAssociation(IContentType contentType, IEditorDescriptor editor) {
		if (this.contentTypeToEditorMappingsFromUser.containsKey(contentType)) {
			this.contentTypeToEditorMappingsFromUser.get(contentType).remove(editor);
		}
		saveAssociations();
	}

	public void addUserAssociation(IContentType contentType, IEditorDescriptor selectedEditor) {
		if (!this.contentTypeToEditorMappingsFromUser.containsKey(contentType)) {
			this.contentTypeToEditorMappingsFromUser.put(contentType, new LinkedHashSet<>());
		}
		this.contentTypeToEditorMappingsFromUser.get(contentType).add(selectedEditor);
		saveAssociations();
	}

