
	private class Snapshot extends RefDirectory {
		private volatile boolean isPackedRefsSnapshot = false;

		private Snapshot() {
			super(parent);
		}

		@Override
		PackedRefList getPackedRefs() throws IOException {
			PackedRefList refs = packedRefs.get();
			if (!isPackedRefsSnapshot) {
				refs = super.getPackedRefs();
				isPackedRefsSnapshot = true;
			}
			return refs;
		}
	}
