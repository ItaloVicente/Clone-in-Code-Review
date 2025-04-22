
		private void updateHiddenState(IWorkbenchPartReference partRef,
				boolean hidden) {
			if (isMe(partRef)) {
				isViewHidden = hidden;
			}
		}

		private boolean isMe(IWorkbenchPartReference partRef) {
			return partRef.getPart(false) == StagingView.this;
		}

		@Override
		public void partDeactivated(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPartReference partRef) {
		}

		@Override
		public void partInputChanged(IWorkbenchPartReference partRef) {
		}
