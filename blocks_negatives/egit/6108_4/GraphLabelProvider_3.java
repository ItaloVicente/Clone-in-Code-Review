	private GitDateFormatter dateFormatter;

	private boolean showEmail;

	private RevCommit lastCommit;

	private PersonIdent lastAuthor;

	private PersonIdent lastCommitter;

	private Format format = Format.LOCALE;

	GraphLabelProvider() {
	}

	public String getColumnText(final Object element, final int columnIndex) {
