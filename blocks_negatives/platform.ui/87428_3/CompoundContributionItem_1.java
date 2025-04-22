    private IMenuListener menuListener = new IMenuListener() {
        @Override
		public void menuAboutToShow(IMenuManager manager) {
            manager.markDirty();
        }
    };
