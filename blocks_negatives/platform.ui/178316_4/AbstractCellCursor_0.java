				if (isVisible()) {
					inFocusRequest = true;
					if (!inFocusRequest) {
						forceFocus();
					}
					inFocusRequest = false;
				}
