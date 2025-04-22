        if (presentationFactory == null) {
            presentationFactory = createDefaultPresentationFactory();
        }
        return presentationFactory;
    }

    /**
     * Creates the default presentation factory by looking up the presentation
     * factory extension with the id specified by the presentation factory preference.
     * If the preference is null or if no matching extension is found, a
     * factory default presentation factory is used.
     */
    private AbstractPresentationFactory createDefaultPresentationFactory() {
        final String factoryId = ((Workbench) window.getWorkbench())
                .getPresentationId();

        if (factoryId != null && factoryId.length() > 0) {
            final AbstractPresentationFactory [] factory = new AbstractPresentationFactory[1];
            StartupThreading.runWithoutExceptions(new StartupRunnable() {

				@Override
				public void runWithException() throws Throwable {
					factory[0] = WorkbenchPlugin.getDefault()
							.getPresentationFactory(factoryId);
				}
			});
            
            if (factory[0] != null) {
                return factory[0];
            }
        }
        PrefUtil.getAPIPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.PRESENTATION_FACTORY_ID,
				IWorkbenchConstants.DEFAULT_PRESENTATION_ID);
