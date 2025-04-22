	protected Frame getCurrentFrame(int flags) {
		Object input = viewer.getInput();
		TreeFrame frame = createFrame(input);
		if ((flags & IFrameSource.FULL_CONTEXT) != 0) {
			frame.setSelection(viewer.getSelection());
			frame.setExpandedElements(viewer.getExpandedElements());
		}
		return frame;
	}
