	private void disposeServices() {
		HashMap<Class<?>, Object> copy = new HashMap<>(servicesToDispose);
		Iterator<Object> iterator = copy.values().iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj instanceof IDisposable) {
				((IDisposable) obj).dispose();
			}
		}
	}

