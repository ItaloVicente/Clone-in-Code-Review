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
