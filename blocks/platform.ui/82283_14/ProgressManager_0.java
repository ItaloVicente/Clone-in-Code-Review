		Set<GroupInfo> localPendingGroupUpdates, localPendingGroupRemoval;
		Map<JobInfo, Set<IJobProgressManagerListener>> localPendingJobUpdates, localPendingJobAddition,
				localPendingJobRemoval;
		synchronized (pendingUpdatesMutex) {
			localPendingJobUpdates = pendingJobUpdates;
			pendingJobUpdates = new LinkedHashMap<>();
			localPendingGroupUpdates = pendingGroupUpdates;
			pendingGroupUpdates = new LinkedHashSet<>();
			localPendingJobRemoval = pendingJobRemoval;
			pendingJobRemoval = new LinkedHashMap<>();
			localPendingGroupRemoval = pendingGroupRemoval;
			pendingGroupRemoval = new LinkedHashSet<>();
			localPendingJobAddition = pendingJobAddition;
			pendingJobAddition = new LinkedHashMap<>();
		}
