			localPendingJobAddition.entrySet()
					.forEach(e -> e.getValue().forEach(listener -> listener.addJob(e.getKey())));

			localPendingJobUpdates.entrySet().stream().map(e -> e.getKey().getGroupInfo()).filter(Objects::nonNull)
					.forEach(localPendingGroupUpdates::add);

			localPendingJobUpdates.entrySet()
					.forEach(e -> e.getValue().forEach(listener -> listener.refreshJobInfo(e.getKey())));
