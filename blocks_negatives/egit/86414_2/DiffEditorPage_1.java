		public TextViewerAction(ITextViewer viewer, int operationCode) {
			code = operationCode;
			target = viewer.getTextOperationTarget();
			update();
		}

		@Override
		public void update() {
			if (code == ITextOperationTarget.REDO)
				return;
