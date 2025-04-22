	private String getToolTipText(Frame nextFrame) {
		if (nextFrame != null) {
			String text = nextFrame.getToolTipText();
			if (text != null && text.length() > 0) {
				return NLS.bind(FrameListMessages.Forward_toolTipOneArg, text);
			}
		}
		return FrameListMessages.Forward_toolTip;
	}
