	private final String[] messages;

	private final Set<CommitFile> files = new TreeSet<CommitFile>();

	/**
	 * Create process with path proposals
	 *
	 * @param messages
	 * @param paths
	 */
	public CommitProposalProcessor(String[] messages, String[] paths) {
		if (messages != null)
			this.messages = messages;
		else
			this.messages = new String[0];

		for (String path : paths) {
			String name = new Path(path).lastSegment();
			if (name == null)
				continue;
			files.add(new CommitFile(name, name));
			int lastDot = name.lastIndexOf('.');
			if (lastDot > 0)
				files.add(new CommitFile(name.substring(0, lastDot), name));
		}
	}

