	static Comparator<DfsPackDescription> commitGraphComparator() {
		return (a
			if (a.getPackSource() == PackSource.GC && b.getPackSource() == PackSource.GC) {
				return Long.signum(b.getLastModified() - a.getLastModified());
			}
			if (a.getPackSource() == PackSource.GC) {
				return -1;
			} else if (b.getPackSource() == PackSource.GC) {
				return 1;
			}
			return 0;
		};
	}

