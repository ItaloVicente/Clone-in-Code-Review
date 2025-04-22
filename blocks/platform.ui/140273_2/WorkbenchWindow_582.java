			IServiceLocatorCreator slc = workbench.getService(IServiceLocatorCreator.class);
			this.serviceLocator = (ServiceLocator) slc.createServiceLocator(workbench, null, new IDisposable() {
				@Override
				public void dispose() {
					final Shell shell = getShell();
					if (shell != null && !shell.isDisposed()) {
						close();
					}
				}
			}, windowContext);
