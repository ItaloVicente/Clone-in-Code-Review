			final DiffFormatter diffFmt, boolean gitFormat) throws IOException {
		if (gitFormat) {
			diffFmt.setRepository(db);
			diffFmt.format(diffEntry);
			return;
		}

		outputEclipseDiff(d, db, diffFmt);
	}

	private void outputEclipseDiff(final StringBuilder d, final Repository db,
			final DiffFormatter diffFmt) throws IOException {
		if (!(getBlobs().length == 2))
