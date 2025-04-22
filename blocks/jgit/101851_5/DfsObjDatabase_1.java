	protected Comparator<DfsReftable> reftableComparator() {
		return (fa
			DfsPackDescription a = fa.getPackDescription();
			DfsPackDescription b = fb.getPackDescription();

			int c = Long.signum(a.getMaxUpdateIndex() - b.getMaxUpdateIndex());
			if (c != 0) {
				return c;
			}

			if (a.getPackSource() == PackSource.GC
					&& b.getPackSource() != PackSource.GC) {
				return -1;
			} else if (b.getPackSource() == PackSource.GC
					&& a.getPackSource() != PackSource.GC) {
				return 1;
			}

			return Long.signum(a.getLastModified() - b.getLastModified());
		};
	}

