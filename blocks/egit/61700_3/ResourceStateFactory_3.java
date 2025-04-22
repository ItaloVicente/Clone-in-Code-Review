		File workTree = repository.getWorkTree();
		String repoRelativePath = path.makeRelativeTo(
				new org.eclipse.core.runtime.Path(workTree.getAbsolutePath()))
				.toString();
		if (repoRelativePath.equals(path.toString())) {
			return result;
		}
		if (file.isContainer()) {
			if (!repoRelativePath.endsWith("/")) { //$NON-NLS-1$
				repoRelativePath += '/';
			}
			if (ResourceUtil.isSymbolicLink(repository, repoRelativePath)) {
				extractFileProperties(indexDiffData, repoRelativePath, result);
			} else {
				extractContainerProperties(indexDiffData, repoRelativePath,
						file, result);
			}
		} else {
			extractFileProperties(indexDiffData, repoRelativePath, result);
