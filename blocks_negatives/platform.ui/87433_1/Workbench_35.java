	private final IBindingManagerListener bindingManagerListener = new IBindingManagerListener() {

		@Override
		public void bindingManagerChanged(BindingManagerEvent bindingManagerEvent) {
			if (bindingManagerEvent.isActiveBindingsChanged()) {
				updateActiveWorkbenchWindowMenuManager(true);
			}
