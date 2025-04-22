		synchronized (pendingUpdatesMutex) {
			rememberListenersForJob(info, pendingJobAddition);
		}
		uiRefreshThrottler.throttledExec();
	}

	private void rememberListenersForJob(JobInfo info, Map<JobInfo, Set<IJobProgressManagerListener>> listenersMap) {
		Set<IJobProgressManagerListener> localListeners = listenersMap.computeIfAbsent(info,
				k -> new LinkedHashSet<>());
		StreamSupport.stream(listeners.spliterator(), false)
				.filter(listener -> !isCurrentDisplaying(info.getJob(), listener.showsDebug()))
				.forEach(localListeners::add);
