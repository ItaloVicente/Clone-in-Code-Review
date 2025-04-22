		pendingGroupUpdates.remove(group);

		safeAsyncExec(() -> {
			for (IJobProgressManagerListener listener : listeners) {
				listener.removeGroup(group);
			}
		});
