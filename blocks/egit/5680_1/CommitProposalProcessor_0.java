	protected abstract Collection<String> computeFileNameProposals();

	protected abstract Collection<String> computeMessageProposals();

	private Set<CommitFile> computeFileProposals() {
		Collection<String> paths = computeFileNameProposals();
		Set<CommitFile> files = new TreeSet<CommitFile>();
		for (String path : paths) {
			String name = new Path(path).lastSegment();
			if (name == null)
				continue;
			files.add(new CommitFile(name, name));
			int lastDot = name.lastIndexOf('.');
			if (lastDot > 0)
				files.add(new CommitFile(name.substring(0, lastDot), name));
		}
		return files;
	}

