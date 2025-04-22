
	private static class MultiURLHyperlinkDetector
			extends URLHyperlinkDetector {

		@Override
		public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
				IRegion region, boolean canShowMultipleHyperlinks) {
			if (region.getLength() == 0) {
				return super.detectHyperlinks(textViewer, region,
						canShowMultipleHyperlinks);
			}
			try {
				String line = textViewer.getDocument().get(region.getOffset(),
						region.getLength());
				int currentOffset = region.getOffset();
				int lineStart = currentOffset;
				int regionEnd = currentOffset + region.getLength();
				List<IHyperlink> allLinks = new ArrayList<>();
				while (currentOffset < regionEnd) {
					IHyperlink[] newLinks = super.detectHyperlinks(
							textViewer, new Region(currentOffset, 0),
							canShowMultipleHyperlinks);
					currentOffset++;
					if (newLinks != null && newLinks.length > 0) {
						Collections.addAll(allLinks, newLinks);
						for (IHyperlink link : newLinks) {
							int end = link.getHyperlinkRegion().getOffset()
									+ link.getHyperlinkRegion().getLength();
							if (end > currentOffset) {
								currentOffset = end;
							}
						}
					}
					int nextCandidatePos = lineStart
							+ line.indexOf("://", currentOffset - lineStart); //$NON-NLS-1$
					if (nextCandidatePos > currentOffset) {
						currentOffset = nextCandidatePos;
					}
				}
				return allLinks.toArray(new IHyperlink[allLinks.size()]);
			} catch (BadLocationException e) {
				return null;
			}
		}

	}
