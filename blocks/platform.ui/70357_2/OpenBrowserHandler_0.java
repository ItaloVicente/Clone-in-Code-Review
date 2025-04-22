			@Override
			public void run() {
				try {
					IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
					IWebBrowser browser = browserSupport.createBrowser(
							IWorkbenchBrowserSupport.LOCATION_BAR | IWorkbenchBrowserSupport.NAVIGATION_BAR, browserId,
							name, tooltip);
					browser.openURL(url); // Must open browser on UI thread
				} catch (PartInitException ex) {
					WebBrowserUIPlugin.logError("error opening browser", ex); //$NON-NLS-1$
				}
			}
		});
