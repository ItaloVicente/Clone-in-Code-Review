		return str;
	}

	private void updateImages(IAction handler) {
		if (handler == null) {
			super.setHoverImageDescriptor(defaultHoverImage);
			super.setImageDescriptor(defaultImage);
			super.setDisabledImageDescriptor(defaultDisabledImage);
		} else {
			ImageDescriptor hoverImage = handler.getHoverImageDescriptor();
			ImageDescriptor image = handler.getImageDescriptor();
			ImageDescriptor disabledImage = handler.getDisabledImageDescriptor();
			if (hoverImage != null || image != null || disabledImage != null) {
				super.setHoverImageDescriptor(hoverImage);
				super.setImageDescriptor(image);
				super.setDisabledImageDescriptor(disabledImage);
			} else {
				super.setHoverImageDescriptor(defaultHoverImage);
				super.setImageDescriptor(defaultImage);
				super.setDisabledImageDescriptor(defaultDisabledImage);
			}
		}
	}
