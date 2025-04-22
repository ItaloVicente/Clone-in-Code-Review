		File workTree = repository.getWorkTree();
		String repoRelativePath = path.makeRelativeTo(
				new org.eclipse.core.runtime.Path(workTree.getAbsolutePath()))
				.toString();
		if (repoRelativePath.equals(path.toString())) {
			return UNKNOWN_STATE;
		}
		ResourceState result = new ResourceState();
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
		}
		return result;
	}

	private void extractFileProperties(@NonNull IndexDiffData indexDiffData,
			@NonNull String repoRelativePath, @NonNull ResourceState state) {
