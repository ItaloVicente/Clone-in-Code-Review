	public void checkChildren(TestElement element, boolean state) {
		int numChildren = element.getChildCount();
		for (int i = 0; i < numChildren; i++) {
			TestElement child = element.getChildAt(i);
			if (fCheckboxViewer.setChecked(child, state))
				checkChildren(child, state);
		}
	}
