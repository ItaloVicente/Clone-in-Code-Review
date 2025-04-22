					host.setCursor(host.getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL));
				}
			} else {
				try {
					layoutUpdateInProgress = true;
					adjustWeights(sashesToDrag, e.x, e.y);
					host.layout();
					host.update();
				} finally {
					layoutUpdateInProgress = false;
