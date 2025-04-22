		display.syncExec(() -> {
			if (localViewer.getControl().isDisposed())
				return;
			SafeRunner.run(new NavigatorSafeRunnable() {
				@Override
				public void run() throws Exception {
					localViewer.getControl().setRedraw(false);
					localViewer.refresh();
				}
			});
			localViewer.getControl().setRedraw(true);
