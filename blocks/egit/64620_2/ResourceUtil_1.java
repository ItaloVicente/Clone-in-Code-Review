			if (existingID == null) {
				markAsUnshared(project);
			} else {
				markAsShared(project);
				boolean isGitProvider = GitProvider.ID.equals(existingID);
				if (isGitProvider) {
					MappingJob.initProviderAsynchronously(project);
				}
