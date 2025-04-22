		ImageRegistry result = imageRegistry;
		if (result == null) { // First check (no locking)
			synchronized (this) {
				result = imageRegistry;
				if (result == null) // Second check (with locking)
					imageRegistry = result = computeFieldValue();
			}
