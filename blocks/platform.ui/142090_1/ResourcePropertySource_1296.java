		}
		if (name.equals(IResourcePropertyConstants.P_DERIVED_RES)) {
			if (element.isDerived())
				return IDEPropertiesMessages.ResourceProperty_true;
			return IDEPropertiesMessages.ResourceProperty_false;
		}
		if (name.equals(IResourcePropertyConstants.P_LINKED_RES)) {
			if (element.isLinked())
				return IDEPropertiesMessages.ResourceProperty_true;
			return IDEPropertiesMessages.ResourceProperty_false;
		}
		if (name.equals(IResourcePropertyConstants.P_LOCATION_RES)) {
			return TextProcessor.process(IDEResourceInfoUtils.getLocationText(element));
		}
		if (name.equals(IResourcePropertyConstants.P_RESOLVED_LOCATION_RES)) {
			return TextProcessor.process(IDEResourceInfoUtils.getResolvedLocationText(element));
		}
		return null;
	}
