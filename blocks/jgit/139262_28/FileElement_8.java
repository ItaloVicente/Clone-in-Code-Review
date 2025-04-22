	public boolean isNullPath() {
		return path.equals(DiffEntry.DEV_NULL);
	}

	public File createTempFile(File directory) throws IOException {
		if (tempFile == null) {
			File file = new File(path);
			if (directory != null) {
				tempFile = getTempFile(file
			} else {
				tempFile = getTempFile(file);
			}
		}
		return tempFile;
	}

	private static File getTempFile(File file) throws IOException {
		return File.createTempFile(".__"
	}

	private static File getTempFile(File file
			String midName) throws IOException {
		String[] fileNameAndExtension = splitBaseFileNameAndExtension(file);
		return File.createTempFile(
				fileNameAndExtension[0] + "_" + midName + "_"
				fileNameAndExtension[1]
	}

	private static File getTempFile(String path
			String midName) throws IOException {
		return getTempFile(new File(path)
	}

