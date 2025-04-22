			if (hoverImage == null && !USE_COLOR_ICONS) {
				hoverImage = image;
			}
			if (disabledImage == null && image != null) {
				disabledImage = ImageDescriptor.createWithFlags(image, SWT.IMAGE_GRAY);
			}
			if (image != null && !USE_COLOR_ICONS) {
				image = ImageDescriptor.createWithFlags(image, SWT.IMAGE_GRAY);
			}
