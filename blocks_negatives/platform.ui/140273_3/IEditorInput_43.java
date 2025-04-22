	 * Note: although a null return value has never been permitted from this
	 * method, there are many known buggy implementations that return null.
	 * Clients that need the image for an editor are advised to use
	 * IWorkbenchPart.getImage() instead of IEditorInput.getImageDescriptor(),
	 * or to recover from a null return value in a manner that records the ID of
	 * the problematic editor input. Implementors that have been returning null
	 * from this method should pick some other default return value (such as
