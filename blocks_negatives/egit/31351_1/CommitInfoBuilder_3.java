	private static final class MessageViewerFormatter extends DiffFormatter {
		private final List<StyleRange> styles;

		private final StringBuilder d;

		private final Color hunkheaderColor;
		private final Color linesAddedColor;
		private final Color linesRemovedColor;

		private MessageViewerFormatter(OutputStream out,
				List<StyleRange> styles, StringBuilder d, Color hunkheaderColor, Color linesAddedColor, Color linesRemovedColor) {
			super(out);
			this.styles = styles;
			this.hunkheaderColor = hunkheaderColor;
			this.linesAddedColor = linesAddedColor;
			this.linesRemovedColor = linesRemovedColor;
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
			styles.add(new StyleRange(start, end - start, hunkheaderColor,
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
					linesAddedColor, null));
		}

		@Override
		protected void writeRemovedLine(RawText b, int bCur) throws IOException {
			flush();
			int start = d.length();
			super.writeRemovedLine(b, bCur);
			flush();
			int end = d.length();
			styles.add(new StyleRange(start, end - start,
					linesRemovedColor, null));
		}
	}

