	private boolean willCreateNewStack(MUIElement dragElement) {
		if (outerRelTo instanceof MPerspectiveStack) {
			return true;
		}
		if (crossSharedAreaBoundary(dragElement, dropStack)) {
			return !getModified();
		}
		return getModified();
