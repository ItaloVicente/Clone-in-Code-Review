	private IPartListener2 partListener;

	boolean visible;

	ProgressViewerContentProvider provider;

	class ActivationListener implements IPartListener2 {

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partClosed(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partOpened(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partHidden(IWorkbenchPartReference partRef) {
			if (isMyPart(partRef)) {
				visible = false;
				provider.stopListening();
			}
		}

		@Override
		public void partVisible(IWorkbenchPartReference partRef) {
			if (isMyPart(partRef)) {
				visible = true;
				provider.startListening();
			}
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
		}

		boolean isMyPart(IWorkbenchPartReference partRef) {
			return ProgressView.this == partRef.getPart(false);
		}

	}
