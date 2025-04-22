	private void disposeServices() {
		HashMap<Class<?>, Object> copy = new HashMap<>(servicesToDispose);
		Set<Entry<Class<?>, Object>> entrySet = copy.entrySet();
		for (Entry<Class<?>, Object> entry : entrySet) {
			if (entry.getValue() instanceof IDisposable) {
				IDisposable iDisposable = (IDisposable) entry.getValue();
				iDisposable.dispose();
			}
			servicesToDispose.remove(entry.getKey());
		}
	}

