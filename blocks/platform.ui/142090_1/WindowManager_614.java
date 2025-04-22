		}
		if (subManagers != null) {
			Iterator<WindowManager> i = subManagers.iterator();
			while (i.hasNext()) {
				WindowManager wm = i.next();
				boolean closed = wm.close();
				if (!closed) {
