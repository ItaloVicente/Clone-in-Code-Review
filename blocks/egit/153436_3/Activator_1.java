	private static boolean isValidRepositoryPath(@NonNull IPath gitDirPath) {
		if (gitDirPath.segmentCount() == 0) {
			return false;
		}
		IPath workingDir = gitDirPath.removeLastSegments(1);
		if (workingDir.isRoot()) {
			return false;
		}
		File userHome = FS.DETECTED.userHome();
		if (userHome != null) {
			Path userHomePath = new Path(userHome.getAbsolutePath());
			if (workingDir.isPrefixOf(userHomePath)) {
				return false;
			}
		}
		return true;
	}

