	private static void autoIgnoreWorkspaceMetaData(java.nio.file.Path gitDir) {
		java.nio.file.Path workspaceRoot = ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile().toPath();
		try (Repository r = FileRepositoryBuilder.create(gitDir.toFile())) {
			if (!r.isBare()
					&& workspaceRoot.startsWith(r.getWorkTree().toPath())) {
				Collection<IPath> ignoredPaths = buildIgnoredPathsList(
						workspaceRoot, ".metadata", //$NON-NLS-1$
						".recommenders"); //$NON-NLS-1$
				JobUtil.scheduleUserJob(new IgnoreOperation(ignoredPaths),
						CoreText.RepositoryUtil_autoIgnoreMetaData,
						JobFamilies.AUTO_IGNORE);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}
	}

	private static Collection<IPath> buildIgnoredPathsList(
			java.nio.file.Path workspaceRoot,
			String... metaDataDirectoryNames) {
		Collection<IPath> ignoredPaths = new HashSet<IPath>();
		for (String m : metaDataDirectoryNames) {
			Path metaData = new Path(
					workspaceRoot.resolve(m).toAbsolutePath().toString());
			try {
				if (RepositoryUtil.canBeAutoIgnored(metaData)) {
					ignoredPaths.add(metaData);
				}
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return ignoredPaths;
	}

