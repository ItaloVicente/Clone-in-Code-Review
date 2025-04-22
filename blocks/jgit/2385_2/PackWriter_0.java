		countingMonitor.beginTask(JGitText.get().countingObjects
				ProgressMonitor.UNKNOWN);

		if (have == null)
			have = Collections.emptySet();

		List<ObjectId> all = new ArrayList<ObjectId>(want.size() + have.size());
		all.addAll(want);
		all.addAll(have);
