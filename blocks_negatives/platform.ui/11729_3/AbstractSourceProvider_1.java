		int emptyIndex = -1;
		for (int i = 0; i < listenerCount; i++) {
			if (listeners[i] == listener) {
				listeners[i] = null;
				emptyIndex = i;
			}
		}

		if (emptyIndex != -1) {
			for (int i = emptyIndex + 1; i < listenerCount; i++) {
				listeners[i - 1] = listeners[i];
			}
			listenerCount--;
		}
