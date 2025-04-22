	/**
	 * Animate the closing of a window given the start position down to the progress
	 * region.
	 *
	 * @param startPosition Rectangle. The position to start drawing from.
	 */
	public static void animateDown(Rectangle startPosition) {
		IWorkbenchWindow currentWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (currentWindow == null) {
			return;
		}
		WorkbenchWindow internalWindow = (WorkbenchWindow) currentWindow;

		ProgressRegion progressRegion = internalWindow.getProgressRegion();
		if (progressRegion == null) {
			return;
		}
		Rectangle endPosition = progressRegion.getControl().getBounds();

		Point windowLocation = internalWindow.getShell().getLocation();
		endPosition.x += windowLocation.x;
		endPosition.y += windowLocation.y;

		AnimationEngine.createTweakedAnimation(internalWindow.getShell(), 400, startPosition, endPosition);
	}

	/**
	 * Animate the opening of a window given the start position down to the progress
	 * region.
	 *
	 * @param endPosition Rectangle. The position to end drawing at.
	 */
	public static void animateUp(Rectangle endPosition) {
		IWorkbenchWindow currentWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (currentWindow == null) {
			return;
		}
		WorkbenchWindow internalWindow = (WorkbenchWindow) currentWindow;
		Point windowLocation = internalWindow.getShell().getLocation();

		ProgressRegion progressRegion = internalWindow.getProgressRegion();
		if (progressRegion == null) {
			return;
		}
		Rectangle startPosition = progressRegion.getControl().getBounds();
		startPosition.x += windowLocation.x;
		startPosition.y += windowLocation.y;

		AnimationEngine.createTweakedAnimation(internalWindow.getShell(), 400, startPosition, endPosition);
	}
