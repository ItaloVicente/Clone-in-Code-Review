	private File extFile(PackExt ext) {
		String p = packFile.getName();
		int dot = p.lastIndexOf('.');
		String b = (dot < 0) ? p : p.substring(0, dot);
		return new File(packFile.getParentFile(), b + '.' + ext.getExtension());
	}

	private boolean hasExt(PackExt ext) {
		return (extensions & ext.getBit()) != 0;
	}

