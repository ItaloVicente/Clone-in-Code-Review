			localPendingGroupUpdates
					.forEach(groupInfo -> listeners.forEach(listener -> listener.refreshGroup(groupInfo)));

			localPendingJobRemoval.entrySet()
					.forEach(e -> e.getValue().forEach(listener -> listener.removeJob(e.getKey())));

			localPendingGroupRemoval.forEach(group -> {
				listeners.forEach(listener -> listener.removeGroup(group));
			});
