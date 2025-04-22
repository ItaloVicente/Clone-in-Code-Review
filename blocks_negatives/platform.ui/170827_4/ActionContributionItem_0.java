			if (USE_COLOR_ICONS) {
				ImageDescriptor image = action.getHoverImageDescriptor();
				if (image == null) {
					image = action.getImageDescriptor();
				}
				ImageDescriptor disabledImage = action
						.getDisabledImageDescriptor();

				if (image == null && forceImage) {
					image = ImageDescriptor.getMissingImageDescriptor();
				}

				LocalResourceManager localManager = new LocalResourceManager(
						parentResourceManager);

				((ToolItem) widget)
						.setDisabledImage(disabledImage == null ? null
								: localManager
										.createImageWithDefault(disabledImage));
				((ToolItem) widget).setImage(image == null ? null
						: localManager.createImageWithDefault(image));

				disposeOldImages();
				imageManager = localManager;

				return image != null;
			}
