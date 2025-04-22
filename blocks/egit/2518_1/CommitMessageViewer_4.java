	private static final class MessageViewerFormatter extends DiffFormatter {
		private final List<StyleRange> styles;

		private final StringBuilder d;

		private MessageViewerFormatter(OutputStream out,
				List<StyleRange> styles, StringBuilder d) {
			super(out);
			this.styles = styles;
			this.d = d;
		}

		@Override
		protected void writeHunkHeader(int aCur, int aEnd, int bCur, int bEnd)
				throws IOException {
			flush();
			int start = d.length();
			super.writeHunkHeader(aCur, aEnd, bCur, bEnd);
			flush();
			int end = d.length();
			styles.add(new StyleRange(start, end - start, SYS_HUNKHEADER_COLOR,
					null));
		}

		@Override
		protected void writeAddedLine(RawText b, int bCur) throws IOException {
			flush();
			int start = d.length();
			super.writeAddedLine(b, bCur);
			flush();
			int end = d.length();
			styles.add(new StyleRange(start, end - start,
					SYS_LINES_ADDED_COLOR, null));
		}

		@Override
		protected void writeRemovedLine(RawText b, int bCur) throws IOException {
			flush();
			int start = d.length();
			super.writeRemovedLine(b, bCur);
			flush();
			int end = d.length();
			styles.add(new StyleRange(start, end - start,
					SYS_LINES_REMOVED_COLOR, null));
		}
	}

	private static final class ObjectLink extends StyleRange {
