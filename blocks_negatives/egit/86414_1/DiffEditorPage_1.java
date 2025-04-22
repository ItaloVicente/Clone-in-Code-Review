		public TextViewerAction(ITextViewer viewer, int operationCode) {
			code = operationCode;
			target = viewer.getTextOperationTarget();
			update();
		}

		@Override
		public void update() {
			if (code == ITextOperationTarget.REDO)
				return;

			boolean wasEnabled = isEnabled();
			boolean isEnabled = target.canDoOperation(code);
			setEnabled(isEnabled);

			if (wasEnabled != isEnabled)
				firePropertyChange(ENABLED, wasEnabled ? Boolean.TRUE
						: Boolean.FALSE, isEnabled ? Boolean.TRUE
						: Boolean.FALSE);
		}
