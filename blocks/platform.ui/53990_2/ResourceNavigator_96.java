					getSite().getShell().getDisplay().asyncExec(() -> {
						linkScheduled = false;

						if (viewer == null || viewer.getControl() == null || viewer.getControl().isDisposed()) {
							return;
						}

						if (dragDetected == false) {
							ResourceNavigator.this.linkToEditor(viewer.getSelection());
