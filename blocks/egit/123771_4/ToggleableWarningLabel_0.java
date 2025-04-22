		if (!isBuiltInImage) {
			image.setImage(PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJS_WARN_TSK));
			isBuiltInImage = true;
		}
		setText(message);
