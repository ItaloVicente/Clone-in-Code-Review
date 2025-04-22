		String browserId = event.getParameter(PARAM_ID_BROWSER_ID);
		String name = event.getParameter(PARAM_ID_NAME);
		String tooltip = event.getParameter(PARAM_ID_TOOLTIP);

		try {
			IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench()
