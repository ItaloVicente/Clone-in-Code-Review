			display.syncExec(() -> {
				if (!display.isDisposed()) {
					display.removeFilter(SWT.Show, rendererFilter);
					display.removeFilter(SWT.Hide, rendererFilter);
					display.removeFilter(SWT.Dispose, rendererFilter);
				}
			});
