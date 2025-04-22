
		safeAsyncExec(() -> {
			for (IJobProgressManagerListener listener : listeners) {
				if (!isCurrentDisplaying(info.getJob(), listener.showsDebug())) {
					listener.removeJob(info);
				}
			}
		});
