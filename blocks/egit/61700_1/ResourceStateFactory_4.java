		if (file.isDirectory()) {
			if (ResourceUtil.isSymbolicLink(repository, repoRelativePath)) {
				extractFileProperties(indexDiffData, repoRelativePath,
						absoluteFile, result);
			} else {
				extractContainerProperties(indexDiffData, repoRelativePath,
						absoluteFile, result);
			}
		} else {
			extractFileProperties(indexDiffData, repoRelativePath, absoluteFile,
					result);
