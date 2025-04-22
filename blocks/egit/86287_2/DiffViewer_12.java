	private class SingleTokenScanner implements ITokenScanner {

		private final String contentType;

		private int currentOffset;

		private int end;

		private int tokenStart;

		public SingleTokenScanner(String contentType) {
			this.contentType = contentType;
		}

		@Override
		public void setRange(IDocument document, int offset, int length) {
			currentOffset = offset;
			end = offset + length;
			tokenStart = -1;
		}

		@Override
		public IToken nextToken() {
			tokenStart = currentOffset;
			if (currentOffset < end) {
				currentOffset = end;
				return tokens.get(contentType);
			}
			return Token.EOF;
		}

		@Override
		public int getTokenOffset() {
			return tokenStart;
		}

		@Override
		public int getTokenLength() {
			return currentOffset - tokenStart;
		}

	}

	private class HyperlinkDetector extends AbstractHyperlinkDetector
			implements IHyperlinkDetectorExtension2 {

		private final Pattern HUNK_LINE_PATTERN = Pattern
				.compile("@@ ([-+]?(\\d+),\\d+) ([-+]?(\\d+),\\d+) @@"); //$NON-NLS-1$

		@Override
		public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
				IRegion region, boolean canShowMultipleHyperlinks) {
			IDocument document = textViewer.getDocument();
			if (textViewer != DiffViewer.this
					|| !(document instanceof DiffDocument)
					|| document.getLength() == 0) {
				return null;
			}
			DiffStyleRange[] ranges = ((DiffDocument) document).getRanges();
			FileDiffRange[] fileRanges = ((DiffDocument) document)
					.getFileRanges();
			if (ranges == null || ranges.length == 0 || fileRanges == null
					|| fileRanges.length == 0) {
				return null;
			}
			int start = region.getOffset();
			int end = region.getOffset() + region.getLength();
			DiffStyleRange key = new DiffStyleRange();
			key.start = start;
			key.length = region.getLength();
			int i = Arrays.binarySearch(ranges, key, (a, b) -> {
				if (a.start > b.start + b.length) {
					return 1;
				}
				if (a.start + a.length < b.start) {
					return -1;
				}
				return 0;
			});
			List<IHyperlink> links = new ArrayList<>();
			FileDiffRange fileRange = null;
			for (; i >= 0 && i < ranges.length; i++) {
				DiffStyleRange range = ranges[i];
				if (range.start >= end) {
					break;
				}
				if (range.start + range.length <= start) {
					continue;
				}
				switch (range.diffType) {
				case HEADLINE:
					fileRange = findFileRange(fileRanges, fileRange,
							range.start);
					if (fileRange != null) {
						DiffEntry.ChangeType change = fileRange.getDiff()
								.getChange();
						switch (change) {
						case ADD:
						case DELETE:
							break;
						default:
							if (getString(document, range.start, range.length)
									.startsWith("diff")) { //$NON-NLS-1$
								IRegion linkRegion = new Region(range.start, 4);
								if (TextUtilities.overlaps(region,
										linkRegion)) {
									links.add(new CompareLink(linkRegion,
											fileRange, -1));
								}
							}
							break;
						}
					}
					break;
				case HEADER:
					fileRange = findFileRange(fileRanges, fileRange,
							range.start);
					if (fileRange != null) {
						String line = getString(document, range.start,
								range.length);
						createHeaderLinks((DiffDocument) document, region,
								fileRange, range, line, DiffEntry.Side.OLD,
								links);
						createHeaderLinks((DiffDocument) document, region,
								fileRange, range, line, DiffEntry.Side.NEW,
								links);
					}
					break;
				case HUNK:
					fileRange = findFileRange(fileRanges, fileRange,
							range.start);
					if (fileRange != null) {
						String line = getString(document, range.start,
								range.length);
						Matcher m = HUNK_LINE_PATTERN.matcher(line);
						if (m.find()) {
							int lineOffset = getContextLines(document, range,
									i + 1 < ranges.length ? ranges[i + 1]
											: null);
							createHunkLinks(region, fileRange, range, m,
									lineOffset, links);
						}
					}
					break;
				default:
					break;
				}
			}
			if (links.isEmpty()) {
				return null;
			}
			return links.toArray(new IHyperlink[links.size()]);
		}

		private String getString(IDocument document, int offset, int length) {
			try {
				return document.get(offset, length);
			} catch (BadLocationException e) {
				return ""; //$NON-NLS-1$
			}
		}

		private int getContextLines(IDocument document, DiffStyleRange hunk,
				DiffStyleRange next) {
			if (next != null) {
				switch (next.diffType) {
				case ADD:
				case REMOVE:
					try {
						int diffLine = document.getLineOfOffset(next.start);
						int hunkLine = document.getLineOfOffset(hunk.start);
						return diffLine - hunkLine - 1;
					} catch (BadLocationException e) {
					}
					break;
				default:
					break;
				}
			}
			return 0;
		}

		private FileDiffRange findFileRange(FileDiffRange[] ranges,
				FileDiffRange candidate, int offset) {
			if (candidate != null && candidate.getStartOffset() <= offset
					&& candidate.getEndOffset() > offset) {
				return candidate;
			}
			FileDiffRange key = new FileDiffRange(null, null, offset, offset);
			int i = Arrays.binarySearch(ranges, key, (a, b) -> {
				if (a.getStartOffset() > b.getEndOffset()) {
					return 1;
				}
				if (b.getStartOffset() > a.getEndOffset()) {
					return -1;
				}
				return 0;
			});
			return i >= 0 ? ranges[i] : null;
		}

		private void createHeaderLinks(DiffDocument document, IRegion region,
				FileDiffRange fileRange, DiffStyleRange range, String line,
				DiffEntry.Side side, List<IHyperlink> links) {
			Pattern p = document.getPathPattern(side);
			if (p == null) {
				return;
			}
			DiffEntry.ChangeType change = fileRange.getDiff().getChange();
			switch (side) {
			case OLD:
				if (change == DiffEntry.ChangeType.ADD) {
					return;
				}
