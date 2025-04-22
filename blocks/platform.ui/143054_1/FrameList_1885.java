		frames.add(frame);
		current = 0;
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		addListenerObject(listener);
	}

	public void back() {
		if (current > 0) {
			setCurrent(current - 1);
		}
	}

	protected void firePropertyChange(PropertyChangeEvent event) {
