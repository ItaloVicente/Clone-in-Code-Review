	private IPageChangedListener pageChangedListener = new IPageChangedListener() {
		@Override
		public void pageChanged(final PageChangedEvent event) {
			for (final IPartListener2 listener : partListener2List) {
				if (listener instanceof IPageChangedListener) {
					SafeRunner.run(new SafeRunnable() {
						@Override
						public void run() throws Exception {
							((IPageChangedListener) listener).pageChanged(event);
						}
					});
				}
