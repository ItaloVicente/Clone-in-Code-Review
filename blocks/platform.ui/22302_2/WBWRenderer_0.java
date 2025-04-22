
	private static void forceLayout(Shell shell) {
		int i = 0;
		while(shell.isLayoutDeferred()) {
			shell.setLayoutDeferred(false);
			i++;
		}
		while(i > 0) {
			shell.setLayoutDeferred(true);
			i--;
		}
	}
