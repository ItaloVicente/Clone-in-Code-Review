		ImageRegistry result = imageRegistry;
		if (result == null) { // First check (no locking)
			synchronized (this) {
				result = imageRegistry;
				if (result == null) {// Second check (with locking)
					ImageRegistry tmp = createImageRegistry();
					initializeImageRegistry(tmp);
					imageRegistry = result = tmp;
				}
			}
