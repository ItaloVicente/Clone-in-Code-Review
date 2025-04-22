		if (getModel().getPersistedState().containsKey(IPreferenceConstants.COOLBAR_VISIBLE)) {
			this.coolBarVisible = Boolean.parseBoolean(getModel().getPersistedState().get(
					IPreferenceConstants.COOLBAR_VISIBLE));
		} else {
			this.coolBarVisible = PrefUtil.getInternalPreferenceStore().getBoolean(
					IPreferenceConstants.COOLBAR_VISIBLE);
			getModel().getPersistedState().put(IPreferenceConstants.COOLBAR_VISIBLE,
					Boolean.toString(this.coolBarVisible));
		}
		if (getModel().getPersistedState().containsKey(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE)) {
			this.perspectiveBarVisible = Boolean.parseBoolean(getModel().getPersistedState().get(
					IPreferenceConstants.PERSPECTIVEBAR_VISIBLE));
		} else {
			this.perspectiveBarVisible = PrefUtil.getInternalPreferenceStore().getBoolean(
					IPreferenceConstants.PERSPECTIVEBAR_VISIBLE);
			getModel().getPersistedState().put(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE,
					Boolean.toString(this.perspectiveBarVisible));
		}

		final IEclipseContext windowContext = model.getContext();
		IServiceLocatorCreator slc = (IServiceLocatorCreator) workbench
				.getService(IServiceLocatorCreator.class);
		this.serviceLocator = (ServiceLocator) slc.createServiceLocator(workbench, null,
				new IDisposable() {
					public void dispose() {
						final Shell shell = getShell();
						if (shell != null && !shell.isDisposed()) {
							close();
						}
					}
				}, windowContext);

		windowContext.set(IExtensionTracker.class.getName(), new ContextFunction() {

			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (tracker == null) {
					tracker = new UIExtensionTracker(getWorkbench().getDisplay());
				}
				return tracker;
