		IEclipsePreferences prefs = getPrefs();

		TreeSet<String> resultStrings = new TreeSet<String>();
		String dirs = prefs.get(PREFS_DIRECTORIES, ""); //$NON-NLS-1$
		if (dirs != null && dirs.length() > 0) {
			StringTokenizer tok = new StringTokenizer(dirs, File.pathSeparator);
			while (tok.hasMoreTokens()) {
				String dirName = tok.nextToken();
				File testFile = new File(dirName);
				if (testFile.exists()) {
					try {
						resultStrings.add(testFile.getCanonicalPath());
					} catch (IOException e) {
						resultStrings.add(testFile.getAbsolutePath());
					}
				}
			}
		}
