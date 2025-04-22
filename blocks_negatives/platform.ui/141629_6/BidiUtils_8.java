			if (LEFT_TO_RIGHT.equals(getTextDirection())) {
				textDir = SWT.LEFT_TO_RIGHT;
			} else if (RIGHT_TO_LEFT.equals(getTextDirection())) {
				textDir = SWT.RIGHT_TO_LEFT;
			} else if (AUTO.equals(getTextDirection())) {
				textDir = SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT;
			}
