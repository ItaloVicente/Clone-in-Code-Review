		if (!untracked.contains(repoRelativePath) && !ignored) {
			if (indexDiffData.getAdded().contains(repoRelativePath))
				tracked = true;
			else {
				try {
					TreeWalk tw = TreeWalk.forPath(repository, repoRelativePath, repository.resolve("HEAD^{tree}")); //$NON-NLS-1$
					if (tw == null)
						tracked = false;
					else
						tracked = true;
				} catch (Exception e) {
					tracked = false;
				}
			}
		}
