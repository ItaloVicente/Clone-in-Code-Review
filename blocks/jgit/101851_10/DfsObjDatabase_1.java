	protected Comparator<DfsReftable> reftableComparator() {
		return (fa
			DfsPackDescription a = fa.getPackDescription();
			DfsPackDescription b = fb.getPackDescription();

			int c = category(b) - category(a);
			if (c != 0) {
				return c;
			}

			c = Long.signum(a.getMaxUpdateIndex() - b.getMaxUpdateIndex());
			if (c != 0) {
				return c;
			}

			return Long.signum(a.getLastModified() - b.getLastModified());
		};
	}

	static int category(DfsPackDescription d) {
		PackSource s = d.getPackSource();
		return s != null ? s.category : 0;
	}

