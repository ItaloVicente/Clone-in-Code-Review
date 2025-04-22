			});
		}
	}

	private final class VisibilityListener extends PartVisibilityListener {

		public VisibilityListener() {
			super(ReflogView.this);
		}

		@Override
		public void partActivated(IWorkbenchPartReference partRef) {
			if (isMe(partRef) && pendingInput != null) {
				ReflogInput input = pendingInput;
				pendingInput = null;
				if (!input.getRepository().getDirectory().exists()) {
					input = null;
				}
				updateView(input);
