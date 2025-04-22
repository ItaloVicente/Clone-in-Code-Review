	public ListenerHandle addChangeListener(ConfigChangedListener listener) {
		return listeners.addConfigChangedListener(listener);
	}

	protected boolean notifyUponTransientChanges() {
		return true;
	}

	protected void fireConfigChangedEvent() {
		listeners.dispatch(new ConfigChangedEvent());
	}

