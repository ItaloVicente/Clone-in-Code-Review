		File resolved = FileKey.resolve(root, FS.DETECTED);
		if (resolved != null) {
			strings.add(resolved.getAbsoluteFile());
			monitor.setTaskName(NLS.bind(
					UIText.RepositorySearchDialog_RepositoriesFound_message,
					Integer.valueOf(strings.size())));
		}

		if ((depth != 0) && !root.equals(resolved)) {
			File[] children = root.listFiles();
			if (children == null) {
