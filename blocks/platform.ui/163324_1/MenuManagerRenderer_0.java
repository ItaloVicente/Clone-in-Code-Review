			Display display = context.get(Display.class);
			UISynchronize uiSynchronize = context.get(UISynchronize.class);
			uiSynchronize.syncExec(() -> {
				display.removeFilter(SWT.Show, rendererFilter);
				display.removeFilter(SWT.Hide, rendererFilter);
				display.removeFilter(SWT.Dispose, rendererFilter);
			});
