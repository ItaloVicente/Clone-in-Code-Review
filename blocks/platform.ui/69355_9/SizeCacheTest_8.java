		Point adjusted = computeHintOffset();

		if (whint != SWT.DEFAULT) {
			whint -= adjusted.x;
		}

		if (hhint != SWT.DEFAULT) {
			hhint -= adjusted.y;
		}

