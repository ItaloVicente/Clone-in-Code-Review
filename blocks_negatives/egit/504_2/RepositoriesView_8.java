		IPath path = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		File root = path.toFile();
		TreeSet<String> dirStrings = new TreeSet<String>();
		recurseDir(root, dirStrings, monitor);
		saveDirStrings(dirStrings);
		scheduleRefresh();
