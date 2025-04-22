		if (!isNeverDisplaying(info.getJob(), showSystemJobs)) {
			for (IJobProgressManagerListener listener : listeners) {
				if (group.isActive())
					listener.refreshGroup(group);
				else
					listener.removeGroup(group);
			}
