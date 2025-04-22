
	static class BitmapObjectFilter extends ObjectFilter {
		private final BitmapBuilder bitmap;

		BitmapObjectFilter(BitmapBuilder bitmap) {
			this.bitmap = bitmap;
		}

		@Override
		public final boolean include(ObjectWalk walker
			throws MissingObjectException
			       IOException {
			return !bitmap.contains(objid);
		}
	}
