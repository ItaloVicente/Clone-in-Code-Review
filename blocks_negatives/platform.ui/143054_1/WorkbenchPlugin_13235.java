        Bundle uiBundle = Platform.getBundle(PlatformUI.PLUGIN_ID);
        try {
        	if(uiBundle != null)
        		uiBundle.start(Bundle.START_TRANSIENT);
        } catch (BundleException e) {
            WorkbenchPlugin.log("Unable to load UI activator", e); //$NON-NLS-1$
        }
