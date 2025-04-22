	private static final InheritableThreadLocal<NLS> local = new InheritableThreadLocal<NLS>() {
		@Override
		protected NLS initialValue() {
			return new NLS(Locale.getDefault());
		}
	};
