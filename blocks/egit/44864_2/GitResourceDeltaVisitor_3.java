		if (resource.getType() == IResource.FOLDER) {
			if (delta.getKind() == IResourceDelta.ADDED) {
				String repoRelativePath = getRepoRelativePath(resource);
				if (repoRelativePath == null) {
					return false;
				}
				String path = repoRelativePath + "/"; //$NON-NLS-1$
				if (isIgnoredInOldIndex(path)) {
					return true; // keep going to catch .gitignore files.
				}
				filesToUpdate.add(path);
				resourcesToUpdate.add(resource);
			}

