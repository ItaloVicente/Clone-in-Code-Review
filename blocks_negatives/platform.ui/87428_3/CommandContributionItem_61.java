	private IBindingManagerListener bindingManagerListener = new IBindingManagerListener() {

		@Override
		public void bindingManagerChanged(BindingManagerEvent event) {
			if (event.isActiveBindingsChanged()
					&& event.isActiveBindingsChangedFor(getCommand())) {
				update();
			}

