		IPath path = UIPlugin.getDefault().getStateLocation();
		StringBuffer fileName = new StringBuffer();
		fileName.append(File.separator);
		fileName.append("TestWorkbenchState");
		fileName.append(File.separator);

		File stateLocation = path.append(fileName.toString()).toFile();
		ensureDirectoryExists(stateLocation);

