
				Hashtable<String, Object> properties = new Hashtable<String, Object>();
				properties.put("id", getId()); //$NON-NLS-1$

				e4WorkbenchService = WorkbenchPlugin.getDefault().getBundleContext()
						.registerService(IWorkbench.class.getName(), this, properties);

