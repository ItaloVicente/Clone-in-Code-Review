		this.titleImage = titleImage;
		firePropertyChange(IWorkbenchPart.PROP_TITLE);
		if (imageDescriptor != null) {
			JFaceResources.getResources().destroyImage(imageDescriptor);
			imageDescriptor = null;
		}
	}

	protected void setTitleToolTip(String toolTip) {
		toolTip = Util.safeString(toolTip);
		if (Util.equals(this.toolTip, toolTip)) {
