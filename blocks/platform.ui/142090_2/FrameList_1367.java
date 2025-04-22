		return (Frame) frames.get(index);
	}

	public IFrameSource getSource() {
		return source;
	}

	public void gotoFrame(Frame frame) {
		for (int i = frames.size(); --i > current;) {
			frames.remove(i);
		}
		frame.setParent(this);
		int index = frames.size();
		frame.setIndex(index);
		frames.add(frame);
		setCurrent(index);
	}

	private void init() {
		Frame frame = source.getFrame(IFrameSource.CURRENT_FRAME, 0);
		frame.setParent(this);
		frame.setIndex(0);
		frames = new ArrayList();
		frames.add(frame);
		current = 0;
	}
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		removeListenerObject(listener);
	}

	public void reset() {
		init();
		firePropertyChange(new PropertyChangeEvent(this, P_RESET,
				null, getFrame(current)));
	}

	void setCurrent(int newCurrent) {
		Assert.isTrue(newCurrent >= 0 && newCurrent < frames.size());
		int oldCurrent = this.current;
		if (oldCurrent != newCurrent) {
			updateCurrentFrame();
			this.current = newCurrent;
			firePropertyChange(new PropertyChangeEvent(this, P_CURRENT_FRAME,
					getFrame(oldCurrent), getFrame(newCurrent)));
		}
	}

	public void setCurrentIndex(int index) {
		if (index != -1 && index != current) {
