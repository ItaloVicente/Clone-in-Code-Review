				Display.getDefault().asyncExec(() -> {
					if (fStyledText.isDisposed()) {
						return;
					}
					if (!fStyledText.getAlwaysShowScrollBars()) {
						fStyledText.setAlwaysShowScrollBars(true);
					}
					if (fScrollBar != null && !fScrollBar.isDisposed()) {
						fScrollBar.setVisible(false);
