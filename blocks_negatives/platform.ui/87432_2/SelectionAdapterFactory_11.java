	private static final ICountable ICOUNT_0 = new ICountable() {
		@Override
		public int count() {
			return 0;
		}
	};
	private static final ICountable ICOUNT_1 = new ICountable() {
		@Override
		public int count() {
			return 1;
		}
	};
	private static final IIterable ITERATE_EMPTY = new IIterable() {
		@Override
		public Iterator iterator() {
			return Collections.EMPTY_LIST.iterator();
		}
	};
