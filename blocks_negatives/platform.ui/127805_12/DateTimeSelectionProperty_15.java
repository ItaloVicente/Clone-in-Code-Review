	private static final ThreadLocal calendar = new ThreadLocal() {
		@Override
		protected Object initialValue() {
			return Calendar.getInstance();
		}
	};
