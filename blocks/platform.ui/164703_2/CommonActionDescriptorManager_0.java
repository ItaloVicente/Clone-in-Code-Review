		IPluginContribution piCont = new IPluginContribution() {
			@Override
			public String getLocalId() {
				return descriptor.getId();
			}

			@Override
			public String getPluginId() {
				return descriptor.getPluginId();
			}
		};

		if (WorkbenchActivityHelper.filterItem(piCont)) {
			return false;
		}

