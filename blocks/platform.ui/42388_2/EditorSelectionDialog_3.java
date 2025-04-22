	    return contents;
	}
	
	private String getFileType() {
		if (fileName == null) {
			return ""; //$NON-NLS-1$
		}
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot == -1 || lastDot >= fileName.length() - 2) {
			return ""; //$NON-NLS-1$
		}
		return fileName.substring(lastDot + 1, fileName.length());
