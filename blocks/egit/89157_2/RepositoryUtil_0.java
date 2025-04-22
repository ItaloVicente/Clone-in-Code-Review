	private static void autoIgnoreWorkspaceMetaData(java.nio.file.Path gitDir) {
		java.nio.file.Path workspaceRoot = ResourcesPlugin.getWorkspace()
				.getRoot().getLocation().toFile().toPath();
		try {
			if (Files.isSameFile(workspaceRoot, gitDir.getParent())) {
				Path metaData = new Path(workspaceRoot.resolve(".metadata") //$NON-NLS-1$
						.toAbsolutePath().toString());
				Collection<IPath> ignore = new HashSet<IPath>();
				ignore.add(metaData);
				JobUtil.scheduleUserJob(
						new IgnoreOperation(ignore),
						CoreText.RepositoryUtil_autoIgnoreMetaData,
						JobFamilies.AUTO_IGNORE);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}
	}

