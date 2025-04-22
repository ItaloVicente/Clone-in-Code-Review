
	private String fixWindowsPath(String path) {
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			return path.replace("\\"
		else
			return path;
	}

