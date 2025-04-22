	public void handleTreeExpanded(TestElement element) {
		boolean checked = fCheckboxViewer.getChecked(element);
		int numChildren = element.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			TestElement child = element.getChildAt(i);
			fCheckboxViewer.setChecked(child, checked);
		}
	}
