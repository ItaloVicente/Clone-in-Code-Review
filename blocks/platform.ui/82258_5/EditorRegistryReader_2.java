		IContentType contentType = null;
		String contentTypeId = element.getAttribute(IWorkbenchRegistryConstants.ATT_CONTENT_TYPE_ID);
		if (contentTypeId == null) {
			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_CONTENT_TYPE_ID);
		} else {
			contentType = Platform.getContentTypeManager().getContentType(contentTypeId);
			if (contentType == null) {
				logError(element, "Unknown content-type with id: " + contentTypeId); //$NON-NLS-1$
			}
		}

		if (descriptor != null && contentType != null) {
			editorRegistry.addContentTypeBinding(contentType, descriptor, false);
		}
		return true;
	}

	private boolean readEditorElement(IConfigurationElement element) {
		String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
