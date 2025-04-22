	/**
	 * A commit object for which a bitmap index should be built.
	 */
	static final class BitmapCommit extends ObjectId {
		private final boolean reuseWalker;
		private final int flags;

		BitmapCommit(AnyObjectId objectId, boolean reuseWalker, int flags) {
			super(objectId);
			this.reuseWalker = reuseWalker;
			this.flags = flags;
		}

		boolean isReuseWalker() {
			return reuseWalker;
		}

		int getFlags() {
			return flags;
		}
	}

