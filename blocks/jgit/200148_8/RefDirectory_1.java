
	public static final class WritableSnapshot extends RefDirectory {
		private volatile boolean isPackedRefsSnapshot;

		public WritableSnapshot(RefDirectory refDb) {
			super(refDb);
		}

		@Override
		PackedRefList getPackedRefs() throws IOException {
			if (!isPackedRefsSnapshot) {
				super.getPackedRefs();
				isPackedRefsSnapshot = true;
			}
			return packedRefs.get();
		}
	}
