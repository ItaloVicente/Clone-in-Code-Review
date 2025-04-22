		if (model.getContext().get(E4Workbench.NO_SAVED_MODEL_FOUND) != null) {
			Point initialSize = getWindowConfigurer().getInitialSize();
			Rectangle bounds = shell.getBounds();
			bounds.width = initialSize.x;
			bounds.height = initialSize.y;
			shell.setBounds(bounds);
		}
