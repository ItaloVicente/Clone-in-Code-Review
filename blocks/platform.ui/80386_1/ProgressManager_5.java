		Collection<JobInfo> infos = jobs.values();
		display.asyncExec(() -> {
			for (IJobProgressManagerListener listener : listeners) {
				listener.refreshAll(infos);
			}
		});
