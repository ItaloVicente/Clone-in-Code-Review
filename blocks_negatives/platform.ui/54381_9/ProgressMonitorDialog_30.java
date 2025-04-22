		shell.addListener(SWT.Show, new Listener() {
			@Override
			public void handleEvent(Event event) {
				shell.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						setMessage(message, true);
					}
				});
			}
		});
