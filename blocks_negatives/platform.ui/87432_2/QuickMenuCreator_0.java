		quickMenu.addListener(SWT.Hide, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!display.isDisposed()) {
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!quickMenu.isDisposed()) {
								quickMenu.dispose();
							}
						}
					});
				}
