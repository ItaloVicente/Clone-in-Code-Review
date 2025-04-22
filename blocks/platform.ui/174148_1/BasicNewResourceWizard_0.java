		if (!runnables.isEmpty()) {
			for (Runnable runnable : runnables) {
				window.getShell().getDisplay().asyncExec(runnable);
			}
		}
	}
