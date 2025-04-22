
			if (image == null && hoverImage != null) {
				image = ImageDescriptor.createWithFlags(action
						.getHoverImageDescriptor(), SWT.IMAGE_GRAY);
			} else {
				if (hoverImage == null && image != null) {
					hoverImage = image;
					image = ImageDescriptor.createWithFlags(action
							.getImageDescriptor(), SWT.IMAGE_GRAY);
				}
			}

			if (hoverImage == null && image == null && forceImage) {
