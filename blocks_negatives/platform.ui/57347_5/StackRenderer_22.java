		editorList.getShell().addListener(SWT.Deactivate, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				editorList.getShell().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						editorList.dispose();
					}
				});
			}
		});
