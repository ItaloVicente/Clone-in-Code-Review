		if ((getShellStyle() & SWT.ON_TOP) != 0 && shell.getParent() != null) {
			parentDeactivateListener = event -> {
				if (listenToParentDeactivate) {
					asyncClose();
				} else {
					listenToParentDeactivate = listenToDeactivate;
				}
			};
			shell.getParent().addListener(SWT.Deactivate,
					parentDeactivateListener);
