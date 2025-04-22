		try {
			IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench()
					.getBrowserSupport();
			IWebBrowser browser = browserSupport.createBrowser(
					IWorkbenchBrowserSupport.LOCATION_BAR
							| IWorkbenchBrowserSupport.NAVIGATION_BAR,
					browserId, name, tooltip);
			browser.openURL(url);
		} catch (PartInitException ex) {
			throw new ExecutionException("error opening browser", ex); //$NON-NLS-1$
		}
