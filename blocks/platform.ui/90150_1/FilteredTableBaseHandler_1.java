	protected String getWorkbenchPartReferenceText(WorkbenchPartReference ref) {
		if (ref.isDirty()) {
			return "*" + ref.getTitle(); //$NON-NLS-1$
		}
		return ref.getTitle();
	}

