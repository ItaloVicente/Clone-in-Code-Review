			Iterator<JobInfo> jobUpdatesIterator = localPendingJobUpdates.iterator();
			while (jobUpdatesIterator.hasNext()) {
				JobInfo info = jobUpdatesIterator.next();

				GroupInfo group = info.getGroupInfo();
				if (group != null) {
					localPendingGroupUpdates.remove(group);
					doRefreshGroup(group);
				}
