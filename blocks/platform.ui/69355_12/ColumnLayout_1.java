			if (wHint == SWT.DEFAULT) {
				sizes[i] = computeControlSize(i, SWT.DEFAULT);
			} else if (wHint == MIN_SIZE) {
				sizes[i] = computeMinimumSize(i);
			} else {
				sizes[i] = computeControlSize(i, cwHint);
			}
