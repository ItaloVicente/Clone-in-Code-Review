		quickMenu.addListener(SWT.Hide, event -> {
			if (!display.isDisposed()) {
				display.asyncExec(() -> {
					if (!quickMenu.isDisposed()) {
						quickMenu.dispose();
					}
				});
