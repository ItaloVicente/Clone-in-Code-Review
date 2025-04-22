	private void notifyPreSaveListeners() {
		notifyLifeCycleManager(PreSave.class);
		notifyWorkbenchListeners(PreSave.class);
	}


	private void notifyPostSaveListeners() {
		notifyLifeCycleManager(PostSave.class);
		notifyWorkbenchListeners(PostSave.class);
	}

	private void notifyLifeCycleManager(Class<? extends Annotation> qualifier) {
		if (lcManager != null) {
			IEclipseContext workbenchContext = workbench.getContext();
			ContextInjectionFactory.invoke(lcManager, PreSave.class, workbenchContext, null);
		}
	}

	private void notifyWorkbenchListeners(Class<? extends Annotation> qualifier) {
		IEclipseContext workbenchContext = workbench.getContext();
		for (Object listener : workbench.getWorkbenchListeners()) {
			ContextInjectionFactory.invoke(listener, qualifier, workbenchContext, null);
		}
	}

