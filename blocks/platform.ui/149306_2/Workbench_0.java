				Dictionary<String, Object> properties = new Hashtable<>();
				properties.put(Constants.SERVICE_RANKING, Integer.valueOf(Integer.MAX_VALUE - 1));
				final ServiceRegistration<?> registration[] = new ServiceRegistration[1];
				StartupMonitor startupMonitor = new StartupMonitor() {
					@Override
					public void applicationRunning() {
						registration[0].unregister(); // unregister ourself
						for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
							IWorkbenchPage page = window.getActivePage();
							if (page != null) {
								((WorkbenchPage) page).fireInitialPartVisibilityEvents();
							}
						}
					}
					@Override
					public void update() {
					}
				};
				registration[0] = WorkbenchPlugin.getDefault().getBundleContext()
						.registerService(StartupMonitor.class.getName(), startupMonitor,
						properties);

