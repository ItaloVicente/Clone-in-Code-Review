			String path = mapping.getRepoRelativePath(resource) + "/"; //$NON-NLS-1$

			if (isIgnoredInOldIndex(entry, path))
				return true; // keep going to catch .gitignore files.

			filesToUpdate.add(path);
