			IServiceLocatorCreator slc = workbench.getService(IServiceLocatorCreator.class);
			this.serviceLocator = (ServiceLocator) slc.createServiceLocator(workbench, null, () -> {
				final Shell shell = getShell();
				if (shell != null && !shell.isDisposed()) {
					close();
				}
			}, windowContext);
