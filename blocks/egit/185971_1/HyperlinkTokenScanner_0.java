					Collections.sort(hyperlinksOnLine, (a, b) -> {
						int diff = a.getHyperlinkRegion().getOffset()
								- b.getHyperlinkRegion().getOffset();
						if (diff != 0) {
							return diff;
						}
						return a.getHyperlinkRegion().getLength()
								- b.getHyperlinkRegion().getLength();
					});
