	private void makeGrayText(StringBuilder d, List<StyleRange> styles) {
		int p0 = 0;
		for (int i = 0; i < styles.size(); ++i) {
			StyleRange r = styles.get(i);
			if (p0 < r.start) {
				StyleRange nr = new StyleRange(p0, r.start - p0, darkGrey,
						null);
				styles.add(i, nr);
				p0 = r.start;
			} else {
				if (r.foreground == null)
					r.foreground = darkGrey;
				p0 = r.start + r.length;
			}
		}
		if (d.length() - 1 > p0) {
			StyleRange nr = new StyleRange(p0, d.length() - p0, darkGrey,
					null);
			styles.add(nr);
		}
	}

