
	public void refresh() {
		fillTags(tagLabelArea.getParent(), getManagedForm().getToolkit());
		fillBranches();
		getManagedForm().getForm().layout(true, true);
	}

