				if (!repoRelativePath.isEmpty()) {
					String path = repoRelativePath + "/"; //$NON-NLS-1$
					if (isIgnoredInOldIndex(path)) {
						return true; // keep going to catch .gitignore files.
					}
					filesToUpdate.add(path);
					resourcesToUpdate.add(resource);
