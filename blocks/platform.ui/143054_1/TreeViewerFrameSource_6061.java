		switch (whichFrame) {
		case IFrameSource.CURRENT_FRAME:
			return getCurrentFrame(flags);
		case IFrameSource.PARENT_FRAME:
			return getParentFrame(flags);
		case IFrameSource.SELECTION_FRAME:
			return getSelectionFrame(flags);
		default:
			return null;
		}
	}
