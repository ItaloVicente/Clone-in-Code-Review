		if (this.fScrollBar == null || !this.fScrollBarSettings.getScrollBarThemed()) {
			return;
		}
		if (!DEBUG_KEEP_NATIVE) {
			if (this.fScrollBar.isVisible()) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						if (fScrollBar != null && !fScrollBar.isDisposed()) {
							fScrollBar.setVisible(false);
						}
					}
				});
			}
		}
