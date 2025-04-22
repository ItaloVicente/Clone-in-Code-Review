	/** Extends generated label adding the resource path for duplicates */
	@Override
	protected String getWorkbenchPartReferenceText(WorkbenchPartReference ref) {
		StringBuilder str = new StringBuilder(super.getWorkbenchPartReferenceText(ref));
		if (ref instanceof EditorReference) {
			str.append(ref.getTitleToolTip());
		}
		return str.toString();
	}

