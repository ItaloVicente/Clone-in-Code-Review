	/**
	 * Subclass the delete action and go into testing mode, which limits user
	 * dialogs.
	 *
	 * @since 3.2
	 */
	private class MyDeleteResourceAction extends DeleteResourceAction {

		public boolean fRan = false;

		public MyDeleteResourceAction(IShellProvider provider) {
			super(provider);
			fTestingMode = true;
		}

		@Override
		public void run() {
			super.run();
			fRan = true;
		}
	}

