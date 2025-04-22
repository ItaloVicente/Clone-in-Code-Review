	private void firePreSave() {
		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void run() throws Exception {
				notifyWorkbenchListeners(PreSave.class);
			}

			@Override
			public void handleException(Throwable exception) {
			}
		});
	}

	private void firePostSave() {
		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void run() throws Exception {
				notifyWorkbenchListeners(PostSave.class);
			}

			@Override
			public void handleException(Throwable exception) {
			}
		});
	}

	private void notifyWorkbenchListeners(Class<? extends Annotation> qualifier) {
		IEclipseContext workbenchContext = workbench.getContext();
		for (Object listener : workbench.getWorkbenchListeners()) {
			ContextInjectionFactory.invoke(listener, qualifier, workbenchContext, null);
		}
	}

