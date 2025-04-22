	public boolean canActivate(IEditorPart editor) {
		MPart mpart = findPart(editor);
		String elementId = mpart.getElementId();
		MPerspective perspective = getCurrentPerspective();
		return partService.isPartOrPlaceholderInPerspective(elementId, perspective);
	}

