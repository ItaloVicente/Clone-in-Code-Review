	private void addDiff(final StringBuilder d,
			final ArrayList<StyleRange> styles) {
		DiffFormatter diffFmt = new DiffFormatter(new OutputStream() {

			@Override
			public void write(int c) throws IOException {
				d.append((char) c);

			}
		}) {
