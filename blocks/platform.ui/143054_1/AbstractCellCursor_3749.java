			case SWT.FocusIn:
				if (isVisible()) {
					inFocusRequest = true;
					if (!inFocusRequest) {
						forceFocus();
					}
					inFocusRequest = false;
