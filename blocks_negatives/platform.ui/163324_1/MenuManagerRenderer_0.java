		Display display = context.get(Display.class);
		if (display != null && !display.isDisposed() && rendererFilter != null) {
			display.removeFilter(SWT.Show, rendererFilter);
			display.removeFilter(SWT.Hide, rendererFilter);
			display.removeFilter(SWT.Dispose, rendererFilter);
		}
