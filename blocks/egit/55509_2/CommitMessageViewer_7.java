	private class CommitDocument extends Document {

		private final List<IHyperlink> hyperlinks;

		private final int headerEnd;

		private final int footerStart;

		public CommitDocument(FormatResult format) {
			super(format.getCommitInfo());
			headerEnd = format.getHeaderEnd();
			footerStart = format.getFooterStart();
			List<ObjectLink> knownLinks = format.getKnownLinks();
			hyperlinks = new ArrayList<>(knownLinks.size());
			for (ObjectLink o : knownLinks) {
				hyperlinks.add(new ObjectHyperlink(o));
			}
			IDocumentPartitioner partitioner = new FastPartitioner(
					new CommitPartitionTokenScanner(),
					new String[] { IDocument.DEFAULT_CONTENT_TYPE,
							HEADER_CONTENT_TYPE, FOOTER_CONTENT_TYPE });
			partitioner.connect(this);
			this.setDocumentPartitioner(partitioner);
		}

		public List<IHyperlink> getKnownHyperlinks() {
			return hyperlinks;
		}

		public int getHeaderEnd() {
			return headerEnd;
		}

		public int getFooterStart() {
			return footerStart;
		}
