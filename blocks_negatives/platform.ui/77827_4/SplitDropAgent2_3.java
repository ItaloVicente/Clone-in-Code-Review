		int side = -1;
		if (where == EModelService.ABOVE) {
			side = SWT.TOP;
		} else if (where == EModelService.BELOW) {
			side = SWT.BOTTOM;
		} else if (where == EModelService.LEFT_OF) {
			side = SWT.LEFT;
		} else if (where == EModelService.RIGHT_OF) {
			side = SWT.RIGHT;
		}

		if (feedback != null)
