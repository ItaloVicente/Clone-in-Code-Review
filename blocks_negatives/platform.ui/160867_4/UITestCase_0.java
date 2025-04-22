	class TestWindowListener implements IWindowListener {
		private boolean enabled = true;

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		@Override
		public void windowActivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowDeactivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowClosed(IWorkbenchWindow window) {
			if (enabled)
				testWindows.remove(window);
		}

		@Override
		public void windowOpened(IWorkbenchWindow window) {
			if (enabled)
				testWindows.add(window);
		}
	}
