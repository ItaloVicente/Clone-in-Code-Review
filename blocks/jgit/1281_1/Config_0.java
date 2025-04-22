	public void addChangeListener(ChangeListener listener) {
		listeners.put(listener
	}

	public void removeChangeListener(ChangeListener listener) {
		listeners.remove(listener);
	}

	protected void changed() {
		EventObject changeEvent = new EventObject(this);
		for (ChangeListener listener : listeners.keySet())
			listener.onChange(changeEvent);
	}

