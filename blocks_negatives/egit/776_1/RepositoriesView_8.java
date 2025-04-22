		List<String> dirStrings = getDirs();
		if (dirStrings.contains(dirString)) {
			return false;
		} else {
			Set<String> dirs = new HashSet<String>();
			dirs.addAll(dirStrings);
			dirs.add(dirString);
			saveDirs(dirs);
			return true;
