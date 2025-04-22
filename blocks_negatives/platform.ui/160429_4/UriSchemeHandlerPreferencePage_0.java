		Collection<UiSchemeInformation> schemeInformationList = Collections.emptyList();
		try {
			schemeInformationList = retrieveSchemeInformationList();
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
					UrlHandlerPreferencePage_Error_Reading_Scheme, e);
			statusManagerWrapper.handle(status, StatusManager.BLOCK | StatusManager.LOG);
