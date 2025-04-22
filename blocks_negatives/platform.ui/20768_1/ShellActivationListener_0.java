		Shell parent = shell.isDisposed() ? null : (Shell) shell.getParent();
		if (parent == null) {
			IEclipseContext currentlyActive = application.getContext()
					.getActiveChild();
			if (currentlyActive != null)
				currentlyActive.deactivate();
			return;
		}
		final IEclipseContext prevChild = (IEclipseContext) parent
				.getData(ECLIPSE_CONTEXT_SHELL_CONTEXT);
		final IEclipseContext parentContext = application.getContext();
		SafeRunner.run(new ISafeRunnable() {
			public void run() throws Exception {
				if (prevChild == null) {
					IEclipseContext activeChild = parentContext.getActiveChild();
					if (activeChild != null) {
						activeChild.deactivate();
					}
				} else {
					prevChild.activate();
				}
			}

			public void handleException(Throwable exception) {
				WorkbenchSWTActivator.trace("/trace/workbench",
						"failed resetting previous child", exception);
			}
		});
