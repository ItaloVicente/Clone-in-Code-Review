	private static Shell getModalChild(Shell[] toSearch) {
		int modal = SWT.APPLICATION_MODAL | SWT.SYSTEM_MODAL | SWT.PRIMARY_MODAL;

		for (int i = toSearch.length - 1; i >= 0; i--) {
			Shell shell = toSearch[i];

			Shell[] children = shell.getShells();
			Shell modalChild = getModalChild(children);
			if (modalChild != null) {
				return modalChild;
			}

			if (shell.isVisible() && (shell.getStyle() & modal) != 0) {
				return shell;
			}
		}

		return null;
	}
