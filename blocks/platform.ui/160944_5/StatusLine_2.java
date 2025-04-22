			inUIThread(() -> {
				showProgress();
				if (animated) {
					if (fProgressBar != null && !fProgressBar.isDisposed()) {
						fProgressBar.beginAnimatedTask();
					}
