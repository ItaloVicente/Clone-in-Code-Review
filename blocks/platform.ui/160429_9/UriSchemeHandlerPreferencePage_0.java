		} catch (Exception e) {
			IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
					UrlHandlerPreferencePage_Error_Reading_Scheme, e);
			statusManagerWrapper.handle(status, StatusManager.BLOCK | StatusManager.LOG);
		}
		return returnList;
	}

	private Collection<UiSchemeInformation> getLoadingSchemeInformationList() {
		Collection<UiSchemeInformation> returnList = new ArrayList<>();
		Collection<IScheme> schemes = extensionReader.getSchemes();
		for (IScheme scheme : schemes) {
			returnList.add(new LoadingSchemeInformation(scheme));
