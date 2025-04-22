	private static final IIterable<?> ITERATE_EMPTY = new IIterable<Object>() {
		@Override
		public Iterator<Object> iterator() {
			return Collections.emptyList().iterator();
		}
	};
