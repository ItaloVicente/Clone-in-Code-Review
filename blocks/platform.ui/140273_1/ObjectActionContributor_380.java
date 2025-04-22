
		if (tag.equals(IWorkbenchRegistryConstants.TAG_ENABLEMENT)) {
			((ObjectContribution) currentContribution).setEnablementTest(element);
			return true;
		}

		return super.readElement(element);
	}

	private boolean testName(Object object) {
		String nameFilter = config.getAttribute(IWorkbenchRegistryConstants.ATT_NAME_FILTER);
		if (nameFilter == null) {
			return true;
		}
		String objectName = null;
		IWorkbenchAdapter de = Adapters.adapt(object, IWorkbenchAdapter.class);
		if (de != null) {
