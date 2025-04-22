	private void addDiff(final StringBuilder d, final ArrayList<StyleRange> styles) {
		DiffFormatter diffFmt = new DiffFormatter() {
			@Override
			protected void writeHunkHeader(OutputStream out, int aCur,
					int aEnd, int bCur, int bEnd) throws IOException {
				int start = d.length();
				super.writeHunkHeader(out, aCur, aEnd, bCur, bEnd);
				int end = d.length();
				styles.add(new StyleRange(start, end - start, sys_hunkHeaderColor, null));
			}
			@Override
			protected void writeAddedLine(OutputStream out, RawText b, int bCur, boolean endOfLineMissing)
					throws IOException {
				int start = d.length();
				super.writeAddedLine(out, b, bCur, endOfLineMissing);
				int end = d.length();
				styles.add(new StyleRange(start, end - start, sys_linesAddedColor, null));
			}
			@Override
			protected void writeRemovedLine(OutputStream out, RawText b, int bCur, boolean endOfLineMissing)
					throws IOException {
				int start = d.length();
				super.writeRemovedLine(out, b, bCur, endOfLineMissing);
				int end = d.length();
				styles.add(new StyleRange(start, end - start, sys_linesRemovedColor, null));
			}
		};

