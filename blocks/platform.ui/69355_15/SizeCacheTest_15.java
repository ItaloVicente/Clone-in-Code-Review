	private Point controlComputeSize(int wHint, int hHint) {
		Point adjusted = computeHintOffset();

		if (wHint != SWT.DEFAULT) {
			wHint -= adjusted.x;
		}

		if (hHint != SWT.DEFAULT) {
			hHint -= adjusted.y;
		}

		return control.computeSize(wHint, hHint, true);
