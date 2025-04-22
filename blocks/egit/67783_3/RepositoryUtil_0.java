	private String relativizeToWorkspace(String pathString) {
		java.nio.file.Path p = java.nio.file.Paths.get(pathString);
		if (p.startsWith(workspacePath)) {
			return workspacePath.relativize(p).toString();
		} else {
			return pathString;
		}
	}

