		Set<String> changed2 = new HashSet<>(baseDiff.getChanged());
		Set<String> removed2 = new HashSet<>(baseDiff.getRemoved());
		Set<String> missing2 = new HashSet<>(baseDiff.getMissing());
		Set<String> modified2 = new HashSet<>(baseDiff.getModified());
		Set<String> untracked2 = new HashSet<>(baseDiff.getUntracked());
		Set<String> conflicts2 = new HashSet<>(baseDiff.getConflicting());
		Set<String> symlinks2 = new HashSet<>(baseDiff.getSymlinks());
		Set<String> submodules2 = new HashSet<>(baseDiff.getSubmodules());
