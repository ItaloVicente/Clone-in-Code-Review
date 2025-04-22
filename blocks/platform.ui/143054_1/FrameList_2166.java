	}

	public int size() {
		return frames.size();
	}

	public void updateCurrentFrame() {
		Assert.isTrue(current >= 0);
		Frame frame = source.getFrame(IFrameSource.CURRENT_FRAME,
				IFrameSource.FULL_CONTEXT);
		frame.setParent(this);
		frame.setIndex(current);
		frames.set(current, frame);
	}
