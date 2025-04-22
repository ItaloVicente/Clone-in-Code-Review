
		@Override
		public void windowOpened(IWorkbenchWindow window) {
		}

		@Override
		public void windowDeactivated(IWorkbenchWindow window) {
		}

		@Override
		public void windowClosed(IWorkbenchWindow window) {
		}

		@Override
		public void windowActivated(IWorkbenchWindow window) {
			schedule(500);
		}
