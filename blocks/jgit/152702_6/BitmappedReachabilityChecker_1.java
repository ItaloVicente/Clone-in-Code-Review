	private static class ReachedFilter extends RevFilter {

		private final BitmapIndex repoBitmaps;
		private final BitmapBuilder reached;

		public ReachedFilter(BitmapIndex repoBitmaps) {
			this.repoBitmaps = repoBitmaps;
			this.reached = repoBitmaps.newBitmapBuilder();
		}

		@Override
		public final boolean include(RevWalk walker
			Bitmap commitBitmap;

			if (reached.contains(cmit)) {
				dontFollow(cmit);
				return false;
			}

			if ((commitBitmap = repoBitmaps.getBitmap(cmit)) != null) {
				reached.or(commitBitmap);
				dontFollow(cmit);
				return true;
			}

			reached.addObject(cmit
			return true;
		}

		private static final void dontFollow(RevCommit cmit) {
			for (RevCommit p : cmit.getParents()) {
				p.add(RevFlag.SEEN);
			}
		}

		@Override
		public final RevFilter clone() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean requiresCommitBody() {
			return false;
		}

		boolean isReachable(RevCommit commit) {
			return reached.contains(commit);
		}
	}
