
	}

	static class KnownHyperlinksDetector implements IHyperlinkDetector {

		@Override
		public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
				IRegion region, boolean canShowMultipleHyperlinks) {
			IDocument document = textViewer.getDocument();
			if (document instanceof CommitDocument) {
				List<IHyperlink> knownLinks = ((CommitDocument) document)
						.getKnownHyperlinks();
				List<IHyperlink> result = new ArrayList<>();
				for (IHyperlink link : knownLinks) {
					IRegion linkRegion = link.getHyperlinkRegion();
					if (TextUtilities.overlaps(linkRegion, region)) {
						result.add(link);
					}
				}
				if (!result.isEmpty()) {
					return result.toArray(new IHyperlink[result.size()]);
				}
			}
