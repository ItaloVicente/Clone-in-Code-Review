		if (name.equals(IBasicPropertyConstants.P_TEXT)) {
			return element.getName();
		}
		if (name.equals(IResourcePropertyConstants.P_PATH_RES)) {
			return TextProcessor.process(element.getFullPath().toString());
		}
		if (name.equals(IResourcePropertyConstants.P_LAST_MODIFIED_RES)) {
			return IDEResourceInfoUtils.getDateStringValue(element);
		}
		if (name.equals(IResourcePropertyConstants.P_EDITABLE_RES)) {
			final ResourceAttributes attributes = element.getResourceAttributes();
