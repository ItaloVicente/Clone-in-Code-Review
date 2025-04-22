
		safeAsyncExec(() -> {
			for (IJobProgressManagerListener listener : listeners) {
				listener.removeGroup(group);
			}
		});
