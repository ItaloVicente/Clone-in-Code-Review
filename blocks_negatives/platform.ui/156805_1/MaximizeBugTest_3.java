		appContext.set(IResourceUtilities.class, new ISWTResourceUtilities() {

			@Override
			public ImageDescriptor imageDescriptorFromURI(URI iconPath) {
				try {
					return ImageDescriptor.createFromURL(new URL(iconPath
							.toString()));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public Image adornImage(Image toAdorn, Image adornment) {
				return null;
			}
		});
