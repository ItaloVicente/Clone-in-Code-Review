
		display = PlatformUI.getWorkbench().getDisplay();

		refreshJobs = new Throttler(display, Duration.ofMillis(100), () -> {
			Iterator<JobInfo> iterator = pendingJobUpdates.iterator();
			while (iterator.hasNext()) {
				JobInfo info = iterator.next();
				if (!pendingJobUpdates.remove(info)) {
					return;
				}

				GroupInfo group = info.getGroupInfo();
				if (group != null) {
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
		});

		refreshGroups = new Throttler(display, Duration.ofMillis(100), () -> {
			Iterator<GroupInfo> iterator = pendingGroupUpdates.iterator();
			while (iterator.hasNext()) {
				GroupInfo groupInfo = iterator.next();
				if (!pendingGroupUpdates.remove(groupInfo)) {
					return;
				}

				doRefreshGroup(groupInfo);
			}
		});
