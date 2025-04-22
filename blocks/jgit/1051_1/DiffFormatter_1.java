	public void format(List<? extends DiffEntry> entries) throws IOException {
		for (DiffEntry ent : entries)
			format(ent);
	}

	public void format(DiffEntry entry) throws IOException {
		if (entry instanceof FileHeader) {
			format(
					(FileHeader) entry
					newRawText(open(entry.getOldMode()
					newRawText(open(entry.getNewMode()
		} else {
			formatAndDiff(entry);
