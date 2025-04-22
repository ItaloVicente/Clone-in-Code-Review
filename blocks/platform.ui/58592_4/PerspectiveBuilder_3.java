	private void addPlaceholderToDefaultFastViewStack(MPartStack stack, String partId) {
		MPlaceholder placeholder = modelService.createModelElement(MPlaceholder.class);
		placeholder.setElementId(partId);
		if (!isDefaultFastView(placeholder)) {
			placeholder.setToBeRendered(false);
		}
		addLayoutTagsToPlaceholder(placeholder, partId);
		stack.getChildren().add(placeholder);
		if (viewPlaceholders.get(partId) != null) {
			viewPlaceholders.put(partId, placeholder);
		}
	}

