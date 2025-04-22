		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, false);
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, false);
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, true);
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, true);
