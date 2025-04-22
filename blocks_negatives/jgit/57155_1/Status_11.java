		StatusCommand statusCommand = new Git(db).status();
		if (filterPaths != null && filterPaths.size() > 0)
			for (String path : filterPaths)
				statusCommand.addPath(path);
		org.eclipse.jgit.api.Status status = statusCommand.call();
		printStatus(status);
