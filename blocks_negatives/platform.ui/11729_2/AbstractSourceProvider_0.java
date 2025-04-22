		if (listenerCount == listeners.length) {
			final ISourceProviderListener[] growArray = new ISourceProviderListener[listeners.length + 4];
			System.arraycopy(listeners, 0, growArray, 0, listeners.length);
			listeners = growArray;
		}
		listeners[listenerCount++] = listener;
