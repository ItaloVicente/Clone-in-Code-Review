		final boolean[] result = new boolean[1];

		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell();
