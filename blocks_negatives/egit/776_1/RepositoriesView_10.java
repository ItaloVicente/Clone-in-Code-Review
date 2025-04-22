		for (String dirString : gitDirStrings) {
			if (monitor != null && monitor.isCanceled()) {
				throw new InterruptedException(
						UIText.RepositoriesView_ActionCanceled_Message);
			}
			try {
				File dir = new File(dirString);
				if (dir.exists() && dir.isDirectory()) {
					Repository repo = new Repository(dir);
					repo.scanForRepoChanges();
					RepositoryNode node = new RepositoryNode(null, repo);
					input.add(node);
