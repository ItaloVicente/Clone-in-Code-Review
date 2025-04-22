		Set<String> changed2 = new HashSet<String>(baseDiff.getChanged());
		Set<String> removed2 = new HashSet<String>(baseDiff.getRemoved());
		Set<String> missing2 = new HashSet<String>(baseDiff.getMissing());
		Set<String> modified2 = new HashSet<String>(baseDiff.getModified());
		Set<String> untracked2 = new HashSet<String>(baseDiff.getUntracked());
		Set<String> conflicts2 = new HashSet<String>(baseDiff.getConflicting());
		Set<String> symlinks2 = new HashSet<String>(baseDiff.getSymlinks());
		Set<String> submodules2 = new HashSet<String>(baseDiff.getSubmodules());
