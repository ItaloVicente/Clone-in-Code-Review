	private RevCommit resolveReflog(RevWalk rw
			throws IOException {
		int number;
		try {
			number = Integer.parseInt(time);
		} catch (NumberFormatException nfe) {
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().invalidReflogRevision
		}
		if (number < 0)
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().invalidReflogRevision

		ReflogReader reader = new ReflogReader(this
		List<ReflogEntry> entries = reader.getReverseEntries(number + 1);
		if (number >= entries.size())
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().reflogEntryNotFound
					Integer.valueOf(number)
					Integer.valueOf(entries.size())));

		return rw.parseCommit(entries.get(number).getNewId());
	}

