	private void notifyPreSave() {
		notifyLifeCycleManager(PreSave.class);
		notifyWorkbenchListeners(PreSave.class);
	}


	private void notifyPostSave() {
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

