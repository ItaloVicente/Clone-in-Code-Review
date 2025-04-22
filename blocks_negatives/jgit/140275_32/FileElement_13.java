	private static File getTempFile(File file) throws IOException {
		return File.createTempFile(".__", "__" + file.getName()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static File getTempFile(File file, File directory, String midName)
			throws IOException {
		String[] fileNameAndExtension = splitBaseFileNameAndExtension(file);
		return File.createTempFile(
				fileNameAndExtension[0] + "_" + midName + "_", //$NON-NLS-1$ //$NON-NLS-2$
				fileNameAndExtension[1], directory);
	}

	private static File getTempFile(String path, File directory, String midName)
			throws IOException {
		return getTempFile(new File(path), directory, midName);
	}

