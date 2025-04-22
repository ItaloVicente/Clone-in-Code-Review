
	@Override
	public String getShowContainerIn() {
		return showContainerIn == null && configElement != null
				? configElement.getAttribute(IWorkbenchRegistryConstants.ATT_SHOW_CONTAINER_IN)
				: showContainerIn;
	}
