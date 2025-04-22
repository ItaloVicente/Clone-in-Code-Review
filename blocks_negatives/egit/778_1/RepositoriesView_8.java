	/**
	 * TODO move to utility class
	 *
	 * @return the directories as configured for this view
	 */
	public static List<String> getDirs() {
		List<String> resultStrings = new ArrayList<String>();
		String dirs = getPrefs().get(PREFS_DIRECTORIES, ""); //$NON-NLS-1$
		if (dirs != null && dirs.length() > 0) {
			StringTokenizer tok = new StringTokenizer(dirs, File.pathSeparator);
			while (tok.hasMoreTokens()) {
				String dirName = tok.nextToken();
				File testFile = new File(dirName);
				if (testFile.exists() && !resultStrings.contains(dirName)) {
					resultStrings.add(dirName);
				}
			}
		}
		Collections.sort(resultStrings);
		return resultStrings;
	}
