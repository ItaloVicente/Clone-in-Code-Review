	@Override
	public void deleteModelElement(@NonNull MUIElement element) {
		Objects.nonNull(element);
		element.setToBeRendered(false);
		if (element.getParent() != null) {
			element.getParent().getChildren().remove(element);
		}

	}

