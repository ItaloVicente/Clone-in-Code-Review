		final BundleContext bundleContext = WorkbenchPlugin.getDefault().getBundleContext();
		final ServiceReference<SignedContentFactory> factoryRef = bundleContext
				.getServiceReference(SignedContentFactory.class);
		if (factoryRef == null) {
			StatusManager.getManager().handle(new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
					WorkbenchMessages.BundleSigningTray_Cant_Find_Service), StatusManager.LOG);
			return;
		}

		final SignedContentFactory contentFactory = bundleContext.getService(factoryRef);
		if (contentFactory == null) {
			StatusManager.getManager().handle(new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
					WorkbenchMessages.BundleSigningTray_Cant_Find_Service), StatusManager.LOG);
			return;
		}
