		if (element.getName().equals(IWorkbenchRegistryConstants.TAG_EDITOR)) {
			return readEditorElement(element);
		}
		if (element.getName().equals(IWorkbenchRegistryConstants.TAG_EDITOR_CONTENT_TYPTE_BINDING)) {
			return readEditorContentTypeBinding(element);
		}
		return false;
    }

	private boolean readEditorContentTypeBinding(IConfigurationElement element) {
		IEditorDescriptor descriptor = null;
		String editorId = element.getAttribute(IWorkbenchRegistryConstants.ATT_EDITOR_ID);
		if (editorId == null) {
			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_EDITOR_ID);
		} else {
			descriptor = editorRegistry.findEditor(editorId);
			if (descriptor == null) {
				logError(element, "Unknown editor with id: " + editorId); //$NON-NLS-1$
			}
