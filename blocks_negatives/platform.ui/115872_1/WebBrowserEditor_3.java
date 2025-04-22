			Image oldImage = image;
			ImageDescriptor id = ImageResource.getImageDescriptor(ImageResource.IMG_INTERNAL_BROWSER);
			image = id.createImage();

			setTitleImage(image);
			if (oldImage != null && !oldImage.isDisposed())
				oldImage.dispose();
