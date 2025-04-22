		synchronized (state) {
			if (state.get().isDone()) {
				runNow = true;
			} else {
				listeners.add(callback);
			}
