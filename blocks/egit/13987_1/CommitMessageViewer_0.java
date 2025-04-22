

						List<StyleRange> styleRangeList = new ArrayList<StyleRange>();

						for (StyleRange styleRange : job.getFormatResult()
								.getStyleRange())
							styleRangeList.add(styleRange);

						StyleRange[] hyperlinkDetectorStyleRanges = getHyperlinkDetectorStyleRanges();

						for (StyleRange styleRange : hyperlinkDetectorStyleRanges)
							styleRangeList.add(styleRange);

						StyleRange[] styleRanges = new StyleRange[styleRangeList
								.size()];
						styleRangeList.toArray(styleRanges);

						Arrays.sort(styleRanges, new Comparator<StyleRange>() {
							public int compare(StyleRange o1, StyleRange o2) {
								if (o2.start > o1.start)
									return -1;
								if (o1.start > o2.start)
									return 1;
								return 0;
							}
						});
						for (StyleRange newStyleRange : styleRanges) {
							text.setStyleRange(newStyleRange);
						}
