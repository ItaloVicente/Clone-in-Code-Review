	private final Function<JobInfo, ScheduledFuture<?>> scheduleRefresh = new Function<JobInfo, ScheduledFuture<?>>() {

		@Override
		public ScheduledFuture<?> apply(JobInfo jobInfo) {
			return executor.schedule(() -> {
				scheduledUpdates.remove(jobInfo);
				GroupInfo group = jobInfo.getGroupInfo();
				if (group != null) {
					refreshGroup(group);
				}

				Object[] listenersArray = listeners.getListeners();
				for (int i = 0; i < listenersArray.length; i++) {
					IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
					if (!isCurrentDisplaying(jobInfo.getJob(), listener.showsDebug())) {
						listener.refreshJobInfo(jobInfo);
					}
				}
			}, 100, TimeUnit.MILLISECONDS);
		}
	};

