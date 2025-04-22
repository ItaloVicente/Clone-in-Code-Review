		doUpdates();
	}

	private void doUpdates() {
		int cnt = pendingUpdates.getAndSet(0);
		if (0 < cnt)
			pm.update(cnt);
