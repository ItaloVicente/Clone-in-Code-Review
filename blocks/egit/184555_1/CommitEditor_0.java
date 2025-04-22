		visibilityListener = new EditorVisibilityTracker(this) {

			private boolean isActive;

			@Override
			public void partActivated(IWorkbenchPartReference partRef) {
				if (isMe(partRef)) {
					if (!isActive) {
						isActive = true;
						updateToolbar();
					}
				} else if (isActive) {
					isActive = false;
					updateToolbar();
				}
			}
		};

