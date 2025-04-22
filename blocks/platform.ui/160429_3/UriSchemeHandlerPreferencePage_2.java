			try {
				for (ISchemeInformation info : operatingSystemRegistration.getSchemesInformation(schemes)) {
					returnList.add(new UiSchemeInformation(info.isHandled(), info));
				}
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
						UrlHandlerPreferencePage_Error_Reading_Scheme, e);
				statusManagerWrapper.handle(status, StatusManager.BLOCK | StatusManager.LOG);
			}
		} else {
			for (IScheme scheme : schemes) {
				ISchemeInformation loadingInfo = ISchemeInformation.getLoadingSchemeInformation(scheme);
				returnList.add(new UiSchemeInformation(loadingInfo.isHandled(), loadingInfo));
