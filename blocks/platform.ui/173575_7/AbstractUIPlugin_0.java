		ScopedPreferenceStore result = preferenceStore;
		if (result == null) { // First check (no locking)
			synchronized (this) {
				result = preferenceStore;
				if (result == null) { // Second check (with locking)
					preferenceStore = result = new ScopedPreferenceStore(InstanceScope.INSTANCE,
							getBundle().getSymbolicName());
				}
			}
