		final Composite parent = shell.getParent();
		if (parent != null) {
			if ((getShellStyle() & SWT.ON_TOP) != 0) {
				parentDeactivateListener = event -> {
					if (listenToParentDeactivate) {
						asyncClose();
					} else {
						listenToParentDeactivate = listenToDeactivate;
					}
				};
				parent.addListener(SWT.Deactivate, parentDeactivateListener);
			} else if (Util.isGtk()) {
				parent.addListener(SWT.Activate, new Listener() {
					@Override
					public void handleEvent(Event event) {
						if (event.widget != parent || !listenToDeactivate || parent.isDisposed()) {
							return;
						}
						parent.removeListener(SWT.Activate, this);
						asyncClose();
					}
				});
			}
