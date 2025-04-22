		return returnList;
	}

	private Collection<UiSchemeInformation> getLoadingSchemeInformationList() {
		Collection<UiSchemeInformation> returnList = new ArrayList<>();
		Collection<IScheme> schemes = extensionReader.getSchemes();
		for (IScheme scheme : schemes) {
			returnList.add(new LoadingSchemeInformation(scheme));
