			shell.addListener(SWT.Deactivate, new Listener() {
				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					updateNonFocusState(SWT.Deactivate, w);
				}
			});
