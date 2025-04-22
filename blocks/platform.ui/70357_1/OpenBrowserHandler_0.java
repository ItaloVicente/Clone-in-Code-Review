		Shell shell = HandlerUtil.getActiveShell(event);
		Display display = shell == null ? Display.getCurrent() : shell.getDisplay();
		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				try {
					IWebBrowser browser = browserSupport.createBrowser(
							IWorkbenchBrowserSupport.LOCATION_BAR | IWorkbenchBrowserSupport.NAVIGATION_BAR, browserId,
							name, tooltip);
					browser.openURL(url);
				} catch (PartInitException ex) {
					throw new RuntimeException("error opening browser", ex); //$NON-NLS-1$
				}
			}
		});
