	public void setRootEntry(IPropertySheetEntry entry) {
		rootEntry = entry;
		if (viewer != null) {
			viewer.setRootEntry(rootEntry);
		}
	}

