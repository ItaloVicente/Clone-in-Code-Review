	public static void storeDefaultImage(Shell shell) {
		storeDefaultImage(shell, DEFAULT_IMAGE, shell.getImage());
	}

	public static void storeDefaultImage(Item item) {
		storeDefaultImage(item, DEFAULT_IMAGE, item.getImage());
	}

	public static void storeDefaultImage(ToolItem item) {
		storeDefaultImage((Item) item);
		storeDefaultImage(item, DEFAULT_HOT_IMAGE, item.getHotImage());
		storeDefaultImage(item, DEFAULT_DISABLE_IMAGE, item.getDisabledImage());
	}

	public static void storeDefaultImage(Button button) {
		storeDefaultImage(button, DEFAULT_IMAGE, button.getImage());
	}

	public static void restoreDefaultImage(Shell shell) {
		Image defaultImage = (Image) shell.getData(DEFAULT_IMAGE);
		if (defaultImage != null) {
			shell.setImage(defaultImage.isDisposed() ? null : defaultImage);
		}
	}

	public static void restoreDefaultImage(Item item) {
		Image defaultImage = (Image) item.getData(DEFAULT_IMAGE);
		if (defaultImage != null) {
			item.setImage(defaultImage.isDisposed() ? null : defaultImage);
		}
	}

	public static void restoreDefaultImage(ToolItem item) {
		restoreDefaultImage((Item) item);

		Image defaultImage = (Image) item.getData(DEFAULT_HOT_IMAGE);
		if (defaultImage != null) {
			item.setHotImage(defaultImage.isDisposed() ? null : defaultImage);
		}

		defaultImage = (Image) item.getData(DEFAULT_DISABLE_IMAGE);
		if (defaultImage != null) {
			item.setDisabledImage(defaultImage.isDisposed() ? null
					: defaultImage);
		}
	}

	public static void restoreDefaultImage(Button button) {
		Image defaultImage = (Image) button.getData(DEFAULT_IMAGE);
		if (defaultImage != null) {
			button.setImage(defaultImage.isDisposed() ? null : defaultImage);
		}
	}

	private static void storeDefaultImage(Widget widget, String imageName,
			Image image) {
		if (widget.getData(imageName) == null) {
			widget.setData(imageName, image);
		}
	}
