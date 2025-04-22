	private boolean filterActionProvider(final CommonActionProviderDescriptor providerDesc) {
		IPluginContribution piCont = new IPluginContribution() {
			@Override
			public String getLocalId() {
				return providerDesc.getId();
			}

			@Override
			public String getPluginId() {
				return providerDesc.getPluginId();
			}
		};

		return WorkbenchActivityHelper.filterItem(piCont);
	}

