	MPlaceholder createPlaceHolder(String str, String label) {
		MPlaceholder placeholder = null;
		placeholder = modelService.createModelElement(MPlaceholder.class);
		placeholder.setElementId(str);
		if (modelService.getPartDescriptor(str) == null) {
			placeholder.getTransientData().put(IWorkbenchConstants.TAG_LABEL, label);
		}
		return placeholder;
	}

