

	private void registerWorkspaceRelativeTestDirProject(String parent, String projName) {
		if ((parent != null) && !parent.equals(""))
			registerWorkspaceRelativeTestDir(parent);
		else
			registerWorkspaceRelativeTestDir(projName);
	}

	private void registerWorkspaceRelativeTestDir(String relativeDir) {
		if ((relativeDir != null) && !relativeDir.equals("")) {
			File d = new File(workspace, relativeDir);
			testDirs.add(d);
		}
	}
