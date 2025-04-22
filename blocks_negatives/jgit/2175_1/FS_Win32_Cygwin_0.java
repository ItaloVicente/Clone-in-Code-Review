		for (final String p : path.split(";")) {
			final File e = new File(p, "cygpath.exe");
			if (e.isFile()) {
				cygpath = e.getAbsolutePath();
				return true;
			}
		}
		return false;
