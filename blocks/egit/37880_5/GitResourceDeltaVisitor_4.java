		if (repoRelativePath == null) {
			resourcesToUpdate.add(resource);
			return true;
		}

		if (isIgnoredInOldIndex(entry, repoRelativePath)) {
			return false;
		}
		filesToUpdate.add(repoRelativePath);
