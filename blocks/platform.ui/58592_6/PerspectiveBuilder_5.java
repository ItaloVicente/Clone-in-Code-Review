	private void addPlaceholderToDefaultFastViewStack(MPartStack stack, String partId, String label) {
		MPlaceholder placeholder = createPlaceHolder(partId, label);
		if (!isDefaultFastView(placeholder)) {
			placeholder.setToBeRendered(false);
		}
		addLayoutTagsToPlaceholder(placeholder, partId);
		stack.getChildren().add(placeholder);
		if (viewPlaceholders.get(partId) != null) {
			viewPlaceholders.put(partId, placeholder);
		}
	}

