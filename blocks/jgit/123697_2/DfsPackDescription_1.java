public class DfsPackDescription {
	public static Comparator<DfsPackDescription> objectLookupComparator() {
		return Comparator.comparing(
					DfsPackDescription::getPackSource
			.thenComparing((a
				PackSource as = a.getPackSource();
				PackSource bs = b.getPackSource();

				if (as == bs && isGC(as)) {
					int cmp = Long.signum(a.getFileSize(PACK) - b.getFileSize(PACK));
					if (cmp != 0) {
						return cmp;
					}
				}

				int cmp = Long.signum(b.getLastModified() - a.getLastModified());
				if (cmp != 0) {
					return cmp;
				}

				return Long.signum(a.getObjectCount() - b.getObjectCount());
			});
	}

	static Comparator<DfsPackDescription> reftableComparator() {
		return (a
				int c = PackSource.DEFAULT_COMPARATOR.reversed()
						.compare(a.getPackSource()
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

	static Comparator<DfsPackDescription> reuseComparator() {
		return (a
			PackSource as = a.getPackSource();
			PackSource bs = b.getPackSource();

			if (as == bs && DfsPackDescription.isGC(as)) {
				return Long.signum(b.getFileSize(PACK) - a.getFileSize(PACK));
			}

			return 0;
		};
	}

