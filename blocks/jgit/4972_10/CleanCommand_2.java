
			Set<String> filtered = filterFolders(
					status.getUntracked()
					status.getUntrackedFolders());

			Set<String> notIgnoredFiles = filterIgnorePaths(filtered
					status.getIgnoredNotInIndex()
			Set<String> notIgnoredDirs = filterIgnorePaths(
					status.getUntrackedFolders()
					status.getIgnoredNotInIndex()

			for (String file : notIgnoredFiles)
