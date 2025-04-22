			if (!child.isDirectory())
				continue;

			File resolved = FileKey.resolve(child, FS.DETECTED);
			if (resolved != null) {
				strings.add(resolved.getAbsoluteFile());
				monitor.setTaskName(NLS.bind(
						UIText.RepositorySearchDialog_RepositoriesFound_message,
						Integer.valueOf(strings.size())));
			}
			else if (lookForNestedRepositories) {
				monitor.subTask(child.getPath());
				findGitDirsRecursive(child, strings, monitor,
						lookForNestedRepositories);
