
			Set<String> untrackedAndIgnoredFiles = new TreeSet<String>(
					status.getUntracked());
			Set<String> untrackedAndIgnoredDirs = new TreeSet<String>(
					status.getUntrackedFolders());

			for (String p : status.getIgnoredNotInIndex()) {
				File f = new File(repo.getWorkTree()
				if (f.isFile()) {
					untrackedAndIgnoredFiles.add(p);
				} else if (f.isDirectory()) {
					untrackedAndIgnoredDirs.add(p);
				}
			}

			Set<String> filtered = filterFolders(untrackedAndIgnoredFiles
					untrackedAndIgnoredDirs);

			Set<String> notIgnoredFiles = filterIgnorePaths(filtered
					status.getIgnoredNotInIndex()
			Set<String> notIgnoredDirs = filterIgnorePaths(
					untrackedAndIgnoredDirs
					status.getIgnoredNotInIndex()

			for (String file : notIgnoredFiles)
