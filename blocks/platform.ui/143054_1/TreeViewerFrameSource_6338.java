	protected void frameChanged(TreeFrame frame) {
		viewer.getControl().setRedraw(false);
		viewer.setInput(frame.getInput());
		if (frame.getExpandedElements() != null)
			viewer.setExpandedElements(frame.getExpandedElements());
		viewer.setSelection(frame.getSelection(), true);
		viewer.getControl().setRedraw(true);
	}
