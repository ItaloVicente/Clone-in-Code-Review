	
	private String getPerspectiveTitle() {
		IPerspectiveDescriptor descriptor = perspective.getDesc();
		String title;
		if (descriptor == null) {
			title = WorkbenchMessages.MissingPerspective_title;
		} else {
			title = descriptor.getLabel();
		}
		return title;
	}
