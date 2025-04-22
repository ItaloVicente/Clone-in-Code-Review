
	/**
	 * A RevFilter that adds the visited commits to {@code bitmap} as a side
	 * effect.
	 * <p>
	 * When the walk hits a commit that is part of {@code bitmap}'s
	 * BitmapIndex, that entire bitmap is ORed into {@code bitmap} and the
	 * commit and its parents are marked as SEEN so that the walk does not
	 * have to visit its ancestors.  This ensures the walk is very short if
	 * there is good bitmap coverage.
	 */
	static class AddToBitmapFilter extends RevFilter {
		private final BitmapBuilder bitmap;

		AddToBitmapFilter(BitmapBuilder bitmap) {
			this.bitmap = bitmap;
		}

		@Override
		public final boolean include(RevWalk walker, RevCommit cmit) {
			Bitmap visitedBitmap;

			if (bitmap.contains(cmit)) {
			} else if ((visitedBitmap = bitmap.getBitmapIndex()
					.getBitmap(cmit)) != null) {
				bitmap.or(visitedBitmap);
			} else {
				bitmap.addObject(cmit, Constants.OBJ_COMMIT);
				return true;
			}

			for (RevCommit p : cmit.getParents()) {
				p.add(RevFlag.SEEN);
			}
			return false;
		}

		@Override
		public final RevFilter clone() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean requiresCommitBody() {
			return false;
		}
	}

	/**
	 * A RevFilter that adds the visited commits to {@code bitmap} as a side
	 * effect.
	 * <p>
	 * When the walk hits a commit that is part of {@code bitmap}'s
	 * BitmapIndex, that entire bitmap is ORed into {@code bitmap} and the
	 * commit and its parents are marked as SEEN so that the walk does not
	 * have to visit its ancestors.  This ensures the walk is very short if
	 * there is good bitmap coverage.
	 * <p>
	 * Commits named in {@code seen} are considered already seen.  If one is
	 * encountered, that commit and its parents will be marked with the SEEN
	 * flag to prevent the walk from visiting its ancestors.
	 */
	static class AddUnseenToBitmapFilter extends RevFilter {
		private final BitmapBuilder seen;
		private final BitmapBuilder bitmap;

		AddUnseenToBitmapFilter(BitmapBuilder seen, BitmapBuilder bitmapResult) {
			this.seen = seen;
			this.bitmap = bitmapResult;
		}

		@Override
		public final boolean include(RevWalk walker, RevCommit cmit) {
			Bitmap visitedBitmap;

			if (seen.contains(cmit) || bitmap.contains(cmit)) {
			} else if ((visitedBitmap = bitmap.getBitmapIndex()
					.getBitmap(cmit)) != null) {
				bitmap.or(visitedBitmap);
			} else {
				bitmap.addObject(cmit, Constants.OBJ_COMMIT);
				return true;
			}

			for (RevCommit p : cmit.getParents()) {
				p.add(RevFlag.SEEN);
			}
			return false;
		}

		@Override
		public final RevFilter clone() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean requiresCommitBody() {
			return false;
		}
	}
