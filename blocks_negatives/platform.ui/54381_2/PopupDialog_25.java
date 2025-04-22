		shell.addListener(SWT.Activate, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.widget == getShell()
						&& getShell().getShells().length == 0) {
					listenToDeactivate = true;
					listenToParentDeactivate = !Util.isMac();
				}
