	private Point controlComputeSize(int wHint, int hHint) {
		Point adjusted = computeHintOffset();

		int adjustedWidthHint = wHint;
		if (adjustedWidthHint != SWT.DEFAULT) {
			adjustedWidthHint = Math.max(0, wHint - adjusted.x);
		}

		int adjustedHeightHint = hHint;
		if (adjustedHeightHint != SWT.DEFAULT) {
			adjustedHeightHint = Math.max(0, hHint - adjusted.y);
		}

		Point result = control.computeSize(adjustedWidthHint, adjustedHeightHint, true);

		if (wHint != SWT.DEFAULT) {
			result.x = wHint;
		}

		if (hHint != SWT.DEFAULT) {
			result.y = hHint;
		}

		return result;
