				e.doit = false; // that would prevent shell from being disposed
			}
		});
		text.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!shell.isDisposed()) {
					storeShellSize();
					shell.close();
					shell.dispose();
				}
