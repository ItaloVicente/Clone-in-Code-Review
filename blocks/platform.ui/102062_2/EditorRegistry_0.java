		contentTypeManager.addContentTypeChangeListener(event -> {
			if (contentTypeManager.getContentType(event.getContentType().getId()) == null) {
				contentTypeToEditorMappingsFromUser.remove(event.getContentType());
				saveAssociations();
			}
		});
