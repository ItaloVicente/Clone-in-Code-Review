		return true;
	}

	public void dispose() {
		if (listeners != null) {
			detachListener();
			listeners = null;
		}
	}

	protected final void firePropertyChange(String prefId) {
		if (ignoreCount > 0) {
			queuedEvents.add(prefId);
			return;
		}

		if (listeners != null) {
			listeners.firePropertyChange(prefId);
		}
	}

	@Override
