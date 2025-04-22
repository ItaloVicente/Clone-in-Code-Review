		if (file.isContainer()) {
			if (!repoRelativePath.endsWith("/")) { //$NON-NLS-1$
				repoRelativePath += '/';
			}
			if (ResourceUtil.isSymbolicLink(repository, repoRelativePath)) {
				extractFileProperties(indexDiffData, repoRelativePath, file,
						result);
			} else {
				extractContainerProperties(indexDiffData, repoRelativePath,
						file, result);
			}
		} else {
			extractFileProperties(indexDiffData, repoRelativePath, file,
					result);
		}
		return result;
	}

	private void extractFileProperties(@NonNull IndexDiffData indexDiffData,
			@NonNull String repoRelativePath, @NonNull FileSystemItem file,
			@NonNull ResourceState state) {
