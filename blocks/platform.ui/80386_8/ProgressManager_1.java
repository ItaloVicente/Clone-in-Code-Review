
		display = PlatformUI.getWorkbench().getDisplay();

		uiRefreshThrottler = new Throttler(display, Duration.ofMillis(100), () -> {
			Iterator<JobInfo> jobUpdatesIterator = pendingJobUpdates.iterator();
			while (jobUpdatesIterator.hasNext()) {
				JobInfo info = jobUpdatesIterator.next();
				if (!pendingJobUpdates.remove(info)) {
					continue;
				}

				GroupInfo group = info.getGroupInfo();
				if (group != null) {
					pendingGroupUpdates.remove(group);
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

			Iterator<GroupInfo> groupUpdatesIterator = pendingGroupUpdates.iterator();
			while (groupUpdatesIterator.hasNext()) {
				GroupInfo groupInfo = groupUpdatesIterator.next();
				if (!pendingGroupUpdates.remove(groupInfo)) {
					continue;
				}

				doRefreshGroup(groupInfo);
			}
		});
