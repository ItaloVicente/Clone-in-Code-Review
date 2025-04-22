		TreeFrame frame = super.createFrame(input);
		frame.setName(navigator.getTitle());
		frame.setToolTipText(navigator.getFrameToolTipText(input));
		return frame;
	}

	@Override
