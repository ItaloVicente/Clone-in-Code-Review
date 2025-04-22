	public void format(DiffEntry entry) throws IOException {
		if (entry instanceof FileHeader) {
			format(
					(FileHeader) entry, //
					newRawText(open(entry.getOldMode(), entry.getOldId())),
					newRawText(open(entry.getNewMode(), entry.getNewId())));
		} else {
			formatAndDiff(entry);
		}
	}

	private void formatAndDiff(DiffEntry ent) throws IOException {
