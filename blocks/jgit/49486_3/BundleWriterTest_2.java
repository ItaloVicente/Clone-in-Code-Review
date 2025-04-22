	private static class NaiveObjectCountCallback
			implements ObjectCountCallback {
		private final boolean value;

		NaiveObjectCountCallback(boolean value) {
			this.value = value;
		}

		@Override
		public boolean setObjectCount(long unused) {
			return value;
		}
	}

