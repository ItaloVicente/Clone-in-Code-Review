				e.doit = false; // That would prevent shell from being disposed
			}
		});
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				storeShellSize();
			}
		});
		text.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!shell.isDisposed()) {
					shell.dispose();
				}
