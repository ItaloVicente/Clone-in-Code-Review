		if (oldInput != null && loader != null) {
			loader.cancel(oldInput);
		}
		currentInput = newInput;
		if (viewer instanceof AbstractTreeViewer && newInput != null) {
			loader = new DeferredTreeContentManager(
					(AbstractTreeViewer) viewer);
		}
