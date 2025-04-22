			Image oldImage = image;
			ImageDescriptor id = wbei.getImageDescriptor();
			image = id.createImage();

			setTitleImage(image);
			if (oldImage != null && !oldImage.isDisposed())
				oldImage.dispose();
