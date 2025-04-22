	@Override
	protected String getWorkbenchPartReferenceText(WorkbenchPartReference ref) {
		if (ref instanceof EditorReference) {
			return WorkbenchMessages.CyclePartAction_editor;
		} else if (ref instanceof ViewReference) {
			return ref.getPartName();
		}
		return super.getWorkbenchPartReferenceText(ref);
	}

