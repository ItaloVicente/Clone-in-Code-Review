		IPath zipFileDirPath = (new Path(zipFile.getName())).removeLastSegments(1);
		String canonicalDestinationDirPath = zipFileDirPath.toString();
		File zipDestinationDir = new File(zipFileDirPath.toString());

		try {
			canonicalDestinationDirPath = zipDestinationDir.getCanonicalPath();
		} catch (IOException e) {
			return;
		}
