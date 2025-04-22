	private static class NaiveObjectCountCallback
			implements ObjectCountCallback {
		private final boolean value;

		NaiveObjectCountCallback(boolean value) {
			this.value = value;
		}

		@Override
		public void setObjectCount(long unused) throws WriteAbortedException {
			if (!value)
				throw new WriteAbortedException();
		}
	}

