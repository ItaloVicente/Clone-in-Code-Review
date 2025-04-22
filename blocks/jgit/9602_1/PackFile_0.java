
	private File extFile(String ext) {
		String p = packFile.getName();
		int dot = p.lastIndexOf('.');
		String b = (dot < 0) ? p : p.substring(0
		return new File(packFile.getParentFile()
	}
