		synchronized (pendingUpdatesMutex) {
			Set<IJobProgressManagerListener> localListeners = pendingJobAddition.computeIfAbsent(info,
					k -> new HashSet<>());
			listenersStream().filter(listener -> !isCurrentDisplaying(info.getJob(), listener.showsDebug()))
					.forEach(localListeners::add);
		}
		uiRefreshThrottler.throttledExec();
	}

	private Stream<IJobProgressManagerListener> listenersStream() {
		Spliterator<?> untype = Arrays.spliterator(listeners.getListeners());
		@SuppressWarnings("unchecked") // Safe as ListenerList.add only allow E
		Spliterator<IJobProgressManagerListener> retype = (Spliterator<IJobProgressManagerListener>) untype;
		return StreamSupport.stream(retype, false);
