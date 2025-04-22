	/**
	 * Creates a workbench part reference for the specified part if one does not
	 * already exist.
	 * 
	 * @param part
	 *            the model part to create a 3.x part reference for
	 */
	private void createReference(MPart part) {
		String uri = part.getContributionURI();
		if (CompatibilityPart.COMPATIBILITY_VIEW_URI.equals(uri)) {
			WorkbenchPage page = getWorkbenchPage(part);
			ViewReference ref = page.getViewReference(part);
			if (ref == null) {
				createViewReference(part, page);
			}
		} else if (CompatibilityPart.COMPATIBILITY_EDITOR_URI.equals(uri)) {
			WorkbenchPage page = getWorkbenchPage(part);
			EditorReference ref = page.getEditorReference(part);
			if (ref == null) {
				createEditorReference(part, page);
			}
		}
	}

