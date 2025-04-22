	private MPartStack addDefaultFastViewStack() {
		MPartStack stack = null;
		List<String> views = perspReader.getDefaultFastViewBarViewIds();
		if (views.size() > 0) {
			stack = layoutUtils.createStack(DEFAULT_FASTVIEW_STACK, true);
			perspective.getChildren().add(stack);
			setPartState(stack, org.eclipse.ui.internal.e4.migration.InfoReader.PartState.MINIMIZED);

			for (String view : views) {
				addPlaceholderToDefaultFastViewStack(stack, view);
			}
		}
		return stack;
	}

