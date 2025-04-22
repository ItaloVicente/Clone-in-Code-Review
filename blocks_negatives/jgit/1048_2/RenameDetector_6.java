	public void addDiffEntry(DiffEntry entry) {
		if (done)
			throw new IllegalStateException(JGitText.get().renamesAlreadyFound);
		switch (entry.changeType) {
		case ADD:
			added.add(entry);
			break;
		case DELETE:
			deleted.add(entry);
			break;
		case COPY:
		case MODIFY:
		case RENAME:
		default:
			entries.add(entry);
		}
