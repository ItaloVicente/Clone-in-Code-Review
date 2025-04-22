
			}
			Matcher m = p.matcher(line);
			if (m.find()) {
				IRegion linkRegion = new Region(range.start + m.start(),
						m.end() - m.start());
				if (TextUtilities.overlaps(region, linkRegion)) {
					if (side == DiffEntry.Side.NEW) {
						File file = new Path(fileRange.getRepository()
								.getWorkTree().getAbsolutePath()).append(
										fileRange.getDiff().getNewPath())
										.toFile();
						if (file.exists()) {
							links.add(new FileLink(linkRegion, file, -1));
						}
					}
					links.add(new OpenLink(linkRegion, fileRange, side, -1));
				}
			}
		}

		private void createHunkLinks(IRegion region, FileDiffRange fileRange,
				DiffStyleRange range, Matcher m, int lineOffset,
				List<IHyperlink> links) {
			DiffEntry.ChangeType change = fileRange.getDiff().getChange();
			if (change != DiffEntry.ChangeType.ADD) {
				IRegion linkRegion = new Region(range.start + m.start(1),
						m.end(1) - m.start(1));
				if (TextUtilities.overlaps(linkRegion, region)) {
					int lineNo = Integer.parseInt(m.group(2)) - 1 + lineOffset;
					if (change != DiffEntry.ChangeType.DELETE) {
						links.add(
								new CompareLink(linkRegion, fileRange, lineNo));
					}
					links.add(new OpenLink(linkRegion, fileRange,
							DiffEntry.Side.OLD, lineNo));
				}
			}
			if (change != DiffEntry.ChangeType.DELETE) {
				IRegion linkRegion = new Region(range.start + m.start(3),
						m.end(3) - m.start(3));
				if (TextUtilities.overlaps(linkRegion, region)) {
					int lineNo = Integer.parseInt(m.group(4)) - 1 + lineOffset;
					if (change != DiffEntry.ChangeType.ADD) {
						links.add(
								new CompareLink(linkRegion, fileRange, lineNo));
					}
					File file = new Path(fileRange.getRepository().getWorkTree()
							.getAbsolutePath())
									.append(fileRange.getDiff().getNewPath())
									.toFile();
					if (file.exists()) {
						links.add(new FileLink(linkRegion, file, lineNo));
					}
					links.add(new OpenLink(linkRegion, fileRange,
							DiffEntry.Side.NEW, lineNo));
				}
			}
		}

		@Override
		public int getStateMask() {
			return -1;
		}
	}

	private static abstract class RevealLink implements IHyperlink {

		private final IRegion region;

		protected final int lineNo;

		protected RevealLink(IRegion region, int lineNo) {
			this.region = region;
			this.lineNo = lineNo;
		}

		@Override
		public IRegion getHyperlinkRegion() {
			return region;
		}

		@Override
		public String getTypeLabel() {
			return null;
		}

	}

	private static class FileLink extends RevealLink {

		private final File file;

		public FileLink(IRegion region, File file, int lineNo) {
			super(region, lineNo);
			this.file = file;
		}

		@Override
		public String getHyperlinkText() {
			return UIText.DiffViewer_OpenWorkingTreeLinkLabel;
		}

		@Override
		public void open() {
			openFileInEditor(file, lineNo);
		}

	}

	private static class CompareLink extends RevealLink {

		protected final Repository repository;

		protected final FileDiff fileDiff;

		public CompareLink(IRegion region, FileDiffRange fileRange,
				int lineNo) {
			super(region, lineNo);
			this.repository = fileRange.getRepository();
			this.fileDiff = fileRange.getDiff();
		}

		@Override
		public String getHyperlinkText() {
			return UIText.DiffViewer_OpenComparisonLinkLabel;
		}

		@Override
		public void open() {
			showTwoWayFileDiff(repository, fileDiff);
		}

	}

	private static class OpenLink extends CompareLink {

		private final DiffEntry.Side side;

		public OpenLink(IRegion region, FileDiffRange fileRange,
				DiffEntry.Side side, int lineNo) {
			super(region, fileRange, lineNo);
			this.side = side;
		}

		@Override
		public String getHyperlinkText() {
			switch (side) {
			case OLD:
				return UIText.DiffViewer_OpenPreviousLinkLabel;
			default:
				return UIText.DiffViewer_OpenInEditorLinkLabel;
