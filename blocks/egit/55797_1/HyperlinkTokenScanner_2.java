		if (hyperlinkDetectors.length > 0) {
			try {
				IRegion currentLine = document
						.getLineInformationOfOffset(currentOffset);
				if (currentLine.getOffset() != lastLineStart) {
					hyperlinksOnLine.clear();
					for (IHyperlinkDetector hyperlinkDetector : hyperlinkDetectors) {
						IHyperlink[] newLinks = hyperlinkDetector
								.detectHyperlinks(viewer, currentLine, false);
						if (newLinks != null && newLinks.length > 0) {
							Collections.addAll(hyperlinksOnLine, newLinks);
						}
					}
					Collections.sort(hyperlinksOnLine,
							new Comparator<IHyperlink>() {
								@Override
								public int compare(IHyperlink a, IHyperlink b) {
									int diff = a.getHyperlinkRegion()
											.getOffset()
											- b.getHyperlinkRegion()
													.getOffset();
									if (diff != 0) {
										return diff;
									}
									return a.getHyperlinkRegion().getLength()
											- b.getHyperlinkRegion()
													.getLength();
								}
							});
					lastLineStart = currentLine.getOffset();
				}
				if (!hyperlinksOnLine.isEmpty()) {
					Iterator<IHyperlink> iterator = hyperlinksOnLine.iterator();
					while (iterator.hasNext()) {
						IHyperlink next = iterator.next();
						IRegion linkRegion = next.getHyperlinkRegion();
						int linkEnd = linkRegion.getOffset()
								+ linkRegion.getLength();
						if (currentOffset >= linkEnd) {
							iterator.remove();
						} else if (linkRegion.getOffset() <= currentOffset) {
							iterator.remove();
							int end = Math.min(endOfRange, linkEnd);
							if (end > currentOffset) {
								currentOffset = end;
								return hyperlinkToken;
							}
						} else {
							break;
						}
					}
