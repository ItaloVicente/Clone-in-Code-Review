	public void format(final OutputStream out, Repository src,
			List<? extends DiffEntry> entries) throws IOException {
		for(DiffEntry ent : entries) {
			if (ent instanceof FileHeader) {
				format(
						out,
						(FileHeader) ent, //
						newRawText(open(src, ent.getOldMode(), ent.getOldId())),
						newRawText(open(src, ent.getNewMode(), ent.getNewId())));
			} else {
				format(out, src, ent);
			}
