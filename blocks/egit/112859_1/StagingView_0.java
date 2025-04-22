		if (!isDisposed()) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
				if (!isDisposed()) {
					runnable.run();
				}
			});
		}
