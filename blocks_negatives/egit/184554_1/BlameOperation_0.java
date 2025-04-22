	/**
	 * Tracks the visibility of an (editor) part and can run code only if the
	 * part is currently visible. If it is not, the code will be run when that
	 * part becomes visible.
	 */
	private static class EditorVisibilityTracker
			extends PartVisibilityListener {

		private Runnable blameComputer;

		public EditorVisibilityTracker(IWorkbenchPart part) {
			super(part);
		}

		public void runWhenVisible(Runnable computer) {
			if (isVisible()) {
				computer.run();
			} else {
				blameComputer = computer;
			}
		}

		@Override
		protected void setVisible(boolean visible) {
			super.setVisible(visible);
			if (visible && blameComputer != null) {
				try {
					blameComputer.run();
				} finally {
					blameComputer = null;
				}
			}
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
		}
	}

