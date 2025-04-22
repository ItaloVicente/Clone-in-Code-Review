	}

	private static String getRepoRelativePath(Repository repo, File file) {
		IPath workdirPath = new Path(repo.getWorkTree().getPath());
		IPath filePath = new Path(file.getPath()).setDevice(null);
		return filePath.removeFirstSegments(workdirPath.segmentCount())
				.toString();
