
	static class BitmapObjectFilter extends ObjectFilter {
		private final BitmapBuilder bitmap;

		public BitmapObjectFilter(BitmapBuilder bitmap) {
			this.bitmap = bitmap;
		}

		@Override
		public final boolean include(ObjectWalk walker
			throws MissingObjectException
			       IOException {
			return !bitmap.contains(objid);
		}
	}
