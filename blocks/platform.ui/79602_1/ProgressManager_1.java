		scheduledUpdates.computeIfAbsent(info, jobInfo -> ex.schedule(() -> {
			scheduledUpdates.remove(jobInfo);
			GroupInfo group = jobInfo.getGroupInfo();
			if (group != null) {
				refreshGroup(group);
			}
