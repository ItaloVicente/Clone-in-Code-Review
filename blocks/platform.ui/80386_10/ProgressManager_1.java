
		display = PlatformUI.getWorkbench().getDisplay();

		uiRefreshThrottler = new Throttler(display, Duration.ofMillis(100), () -> {
			Set<JobInfo> localPendingJobUpdates;
			Set<GroupInfo> localPendingGroupUpdates;
			synchronized (pendingUpdatesMutex) {
				localPendingJobUpdates = pendingJobUpdates;
				pendingJobUpdates = new HashSet<>();
				localPendingGroupUpdates = pendingGroupUpdates;
				pendingGroupUpdates = new HashSet<>();
			}
			Iterator<JobInfo> jobUpdatesIterator = localPendingJobUpdates.iterator();
			while (jobUpdatesIterator.hasNext()) {
				JobInfo info = jobUpdatesIterator.next();

				GroupInfo group = info.getGroupInfo();
				if (group != null) {
					localPendingGroupUpdates.remove(group);
					doRefreshGroup(group);
				}

				Object[] listenersArray = listeners.getListeners();
				for (int i = 0; i < listenersArray.length; i++) {
					IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
					if (!isCurrentDisplaying(info.getJob(), listener.showsDebug())) {
						listener.refreshJobInfo(info);
					}
				}
			}

			Iterator<GroupInfo> groupUpdatesIterator = localPendingGroupUpdates.iterator();
			while (groupUpdatesIterator.hasNext()) {
				GroupInfo groupInfo = groupUpdatesIterator.next();
				doRefreshGroup(groupInfo);
			}
		});
