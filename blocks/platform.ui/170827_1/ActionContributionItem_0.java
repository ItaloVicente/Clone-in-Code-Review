			if (image == null && hoverImage != null) {
				image = hoverImage;
			}
			if (hoverImage == null && image != null) {
				hoverImage = image;
			}
			if (disabledImage == null && image != null) {
				disabledImage = ImageDescriptor.createWithFlags(image, SWT.IMAGE_GRAY);
			}
			if (image != null && !USE_COLOR_ICONS) {
				image = ImageDescriptor.createWithFlags(image, SWT.IMAGE_GRAY);
			}
