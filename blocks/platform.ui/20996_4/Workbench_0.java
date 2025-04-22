				Hashtable<String, Object> properties = new Hashtable<String, Object>();
				properties.put("id", getId()); //$NON-NLS-1$

				workbenchService = WorkbenchPlugin.getDefault().getBundleContext()
						.registerService(IWorkbench.class.getName(), this, properties);

				e4WorkbenchService = WorkbenchPlugin.getDefault().getBundleContext()
						.registerService(org.eclipse.e4.ui.workbench.IWorkbench.class.getName(),
								this, properties);

