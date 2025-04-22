		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty()) {
				detachListener();
				listeners = null;
			}
		}
	}

	@Override
