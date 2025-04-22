		File resolved = FileKey.resolve(root, FS.DETECTED);
		if ((resolved != null) && !suppressed(root, resolved)) {
			gitDirs.add(resolved.getAbsoluteFile());
			monitor.setTaskName(NLS.bind(
					UIText.RepositorySearchDialog_RepositoriesFound_message,
					Integer.valueOf(gitDirs.size())));
		}
