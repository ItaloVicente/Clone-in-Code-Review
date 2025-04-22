
			String repoRelativePath = mapping.getRepoRelativePath(resource)
					+ "/"; //$NON-NLS-1$

			if (isIgnoredInOldIndex(entry, repoRelativePath)) {
				return false; // the directory is ignored - stop recursion here.
			}

			filesToUpdate.add(repoRelativePath);
