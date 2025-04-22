		final boolean showMinimized = !(hideMinimize || (isMinimized && hideRestore));
		final boolean showMaximized = !(hideMaximize || (isMaximized && hideRestore));

		ctf.setMinimizeVisible(true);
		ctf.setMaximizeVisible(true);

		Display display = Display.findDisplay(Thread.currentThread());
		display.asyncExec(() -> {
			if (ctf.isDisposed()) {
				return;
			}
			if (isMinimized) {
				ctf.setMinimized(true);
			} else if (isMaximized) {
