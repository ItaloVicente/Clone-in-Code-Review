
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
