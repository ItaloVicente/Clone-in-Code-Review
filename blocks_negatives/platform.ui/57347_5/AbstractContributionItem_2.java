	protected IMenuListener menuListener = new IMenuListener() {
		@Override
		public void menuAboutToShow(IMenuManager manager) {
			update(null);
		}
	};
