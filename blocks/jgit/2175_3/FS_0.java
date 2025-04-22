
	static File searchPath(final String path
		for (final String p : path.split(File.pathSeparator)) {
			for (String command : lookFor) {
				final File e = new File(p
				if (e.isFile())
					return e.getAbsoluteFile();
			}
		}
		return null;
	}
