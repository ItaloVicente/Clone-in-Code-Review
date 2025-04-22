		if (listeners == null) {
			listeners = new PropertyListenerList();
			attachListener();
		}
		listeners.add(eventsOfInterest, listener);
	}

	protected final void firePropertyChange(String[] prefIds) {
		if (ignoreCount > 0) {
			for (String prefId : prefIds) {
				queuedEvents.add(prefId);
			}
			return;
		}

		if (listeners != null) {
			listeners.firePropertyChange(prefIds);
		}
	}

	public final void startTransaction() {
		ignoreCount++;
	}

	public final void endTransaction() {
		ignoreCount--;
		if (ignoreCount == 0 && !queuedEvents.isEmpty()) {
			if (listeners != null) {
				listeners.firePropertyChange((String[]) queuedEvents.toArray(new String[queuedEvents.size()]));
			}
			queuedEvents.clear();
		}
	}

	@Override
