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
