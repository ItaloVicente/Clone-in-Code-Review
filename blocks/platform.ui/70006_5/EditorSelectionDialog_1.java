	private static String getFileExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		int index = fileName.lastIndexOf('.');
		if (index != -1) {
			return fileName.substring(index);
		}
		return fileName;
	}

