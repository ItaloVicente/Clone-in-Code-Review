		Iterator<Object> i = servicesToDispose.values().iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			if (obj instanceof IDisposable) {
				((IDisposable) obj).dispose();
			}
