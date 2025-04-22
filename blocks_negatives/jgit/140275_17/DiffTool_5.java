		for (String name : userTools.keySet()) {
					+ userTools.get(name).getCommand());
		}
		outw.println(
				"The following tools are valid, but not currently available:"); //$NON-NLS-1$
		for (String name : predefTools.keySet()) {
			if (!predefTools.get(name).isAvailable()) {
			}
		}
		outw.println(
				"environment. If run in a terminal-only session, they will fail."); //$NON-NLS-1$
		outw.flush();
		return;
	}

	private List<DiffEntry> getFiles()
			throws RevisionSyntaxException, AmbiguousObjectException,
			IncorrectObjectTypeException, IOException {
		diffFmt.setRepository(db);
		if (cached) {
			if (oldTree == null) {
				if (head == null) {
					die(MessageFormat.format(CLIText.get().notATree, HEAD));
