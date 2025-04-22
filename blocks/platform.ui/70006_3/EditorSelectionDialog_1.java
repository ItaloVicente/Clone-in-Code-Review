	private static String getFileExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index != -1) {
			return fileName.substring(index);
		}
		return fileName;
	}

