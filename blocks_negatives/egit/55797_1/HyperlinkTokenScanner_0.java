		for (IHyperlinkDetector hyperlinkDetector : hyperlinkDetectors) {
			IHyperlink[] newLinks = hyperlinkDetector.detectHyperlinks(viewer,
					new Region(currentOffset, 0), false);
			if (newLinks != null && newLinks.length > 0) {
				IRegion region = newLinks[0].getHyperlinkRegion();
				int end = Math.min(endOfRange,
						region.getOffset() + region.getLength());
				if (end > tokenStart) {
					currentOffset = end;
					return hyperlinkToken;
