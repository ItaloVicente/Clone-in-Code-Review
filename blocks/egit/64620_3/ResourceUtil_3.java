			System.out.println("Persistent property access " + (++count)); //$NON-NLS-1$
			if (existingID == null) {
				markAsUnshared(project);
			} else {
				markAsShared(project, existingID);
				boolean isGitProvider = GitProvider.ID.equals(existingID);
				if (isGitProvider) {
					MappingJob.initProviderAsynchronously(project);
				}
