		if (sel.size() == 1) {
			Object o = sel.getFirstElement();
			if (viewer.isExpandable(o)) {
				TreeFrame frame = createFrame(o);
				if ((flags & IFrameSource.FULL_CONTEXT) != 0) {
					frame.setSelection(viewer.getSelection());
					frame.setExpandedElements(viewer.getExpandedElements());
				}
				return frame;
			}
		}
		return null;
	}
