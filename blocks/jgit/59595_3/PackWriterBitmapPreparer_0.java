	private static class NotInBitmapFilter extends RevFilter {
		private final BitmapBuilder bitmap;

		NotInBitmapFilter(BitmapBuilder bitmap) {
			this.bitmap = bitmap;
		}

		@Override
		public final boolean include(RevWalk rw
			if (!bitmap.contains(c)) {
				return true;
			}
			for (RevCommit p : c.getParents()) {
				p.add(SEEN);
			}
			return false;
		}

		@Override
		public final NotInBitmapFilter clone() {
			return this;
		}

		@Override
		public final boolean requiresCommitBody() {
			return false;
		}
	}

