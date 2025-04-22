		HandlerUtil.getActiveShellChecked(event).getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				try {
					IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
					IWebBrowser browser = browserSupport.createBrowser(
							IWorkbenchBrowserSupport.LOCATION_BAR | IWorkbenchBrowserSupport.NAVIGATION_BAR, browserId,
							name, tooltip);
				} catch (PartInitException ex) {
					WebBrowserUtil.openError(NLS.bind(Messages.errorCouldNotLaunchWebBrowser, url));
				}
