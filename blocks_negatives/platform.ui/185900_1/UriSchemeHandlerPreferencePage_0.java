	/**
	 * Schemes which are part of extension points for URI Schemes and are registered
	 * to operating system are consolidated here
	 *
	 * @return the supported and registered URI schemes of this instance of eclipse
	 * @throws Exception
	 */
	private Collection<UiSchemeInformation> retrieveSchemeInformationList() {
		Collection<UiSchemeInformation> returnList = new ArrayList<>();
		Collection<IScheme> schemes = extensionReader.getSchemes();
		try {
			for (ISchemeInformation info : operatingSystemRegistration.getSchemesInformation(schemes)) {
				returnList.add(new UiSchemeInformation(info.isHandled(), info));
			}
		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
					UrlHandlerPreferencePage_Error_Reading_Scheme, e);
			statusManagerWrapper.handle(status, StatusManager.BLOCK | StatusManager.LOG);
		}
		return returnList;
	}

