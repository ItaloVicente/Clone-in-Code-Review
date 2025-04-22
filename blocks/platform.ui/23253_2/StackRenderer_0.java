			Bundle bundle = FrameworkUtil.getBundle(this.getClass());
			URL url = FileLocator.find(bundle, new Path(
					"icons/full/ovr16/view_dropdown.png"), null); //$NON-NLS-1$
			ImageDescriptor imageDescriptor = ImageDescriptor
					.createFromURL(url);
			viewMenuImage = imageDescriptor.createImage();
