	private IToolBarManager toolbar;

	private IPartListener activationListener = new IPartListener() {

		private boolean isActive;

		@Override
		public void partActivated(IWorkbenchPart part) {
			if (part == CommitEditor.this) {
				if (!isActive) {
					isActive = true;
					updateToolbar();
				}
			} else if (isActive) {
				isActive = false;
				updateToolbar();
			}
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
		}

		@Override
		public void partClosed(IWorkbenchPart part) {
		}

		@Override
		public void partDeactivated(IWorkbenchPart part) {
		}

		@Override
		public void partOpened(IWorkbenchPart part) {
		}

	};

