					getSite().getShell().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							linkScheduled = false;

							if (viewer == null || viewer.getControl() == null || viewer.getControl().isDisposed()) {
								return;
							}

							if (dragDetected == false) {
								ResourceNavigator.this.linkToEditor(viewer.getSelection());
							}
