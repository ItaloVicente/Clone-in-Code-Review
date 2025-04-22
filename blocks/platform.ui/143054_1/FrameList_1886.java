			((IPropertyChangeListener) listener).propertyChange(event);
		}
	}

	public void forward() {
		if (current < frames.size() - 1) {
			setCurrent(current + 1);
		}
	}

	public Frame getCurrentFrame() {
		return getFrame(current);
	}

	public int getCurrentIndex() {
		return current;
	}

	public Frame getFrame(int index) {
		if (index < 0 || index >= frames.size()) {
