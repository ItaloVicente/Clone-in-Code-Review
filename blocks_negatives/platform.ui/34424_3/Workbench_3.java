	/**
	 * Return the presentation ID specified by the preference or the default ID
	 * if undefined.
	 * 
	 * @return the presentation ID
	 * @see IWorkbenchPreferenceConstants#PRESENTATION_FACTORY_ID
	 */
	public String getPresentationId() {
		if (factoryID != null) {
			return factoryID;
		}

		factoryID = PrefUtil.getAPIPreferenceStore().getString(
				IWorkbenchPreferenceConstants.PRESENTATION_FACTORY_ID);

			factoryID = IWorkbenchConstants.DEFAULT_PRESENTATION_ID;
		}
		return factoryID;
	}

