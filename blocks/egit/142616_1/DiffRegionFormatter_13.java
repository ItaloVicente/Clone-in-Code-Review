
		@Override
		public <T> T getAdapter(Class<T> adapter) {
			if (FileDiff.class.equals(adapter)) {
				return adapter.cast(diff);
			}
			return null;
		}
