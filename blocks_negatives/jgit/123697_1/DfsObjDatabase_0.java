		return (fa, fb) -> {
			DfsPackDescription a = fa.getPackDescription();
			DfsPackDescription b = fb.getPackDescription();

			int c = PackSource.DEFAULT_COMPARATOR.reversed()
					.compare(a.getPackSource(), b.getPackSource());
			if (c != 0) {
				return c;
			}

			c = Long.signum(a.getMaxUpdateIndex() - b.getMaxUpdateIndex());
			if (c != 0) {
				return c;
			}

			return Long.signum(a.getLastModified() - b.getLastModified());
		};
