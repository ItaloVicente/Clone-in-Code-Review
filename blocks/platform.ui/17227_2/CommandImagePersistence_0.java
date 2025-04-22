		IProduct product = Platform.getProduct();
		if (product != null) {
			Bundle productBundle = product.getDefiningBundle();
			if (productBundle != null) {
				String imageList = product.getProperty("windowImages"); //$NON-NLS-1$
				if (imageList != null) {
					String iconPath = imageList.split(",")[0]; //$NON-NLS-1$
					URL iconUrl = productBundle.getEntry(iconPath);
					ImageDescriptor icon = ImageDescriptor.createFromURL(iconUrl);
					if (icon != null) {
						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
								CommandImageManager.TYPE_DEFAULT, null, icon);
						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
								CommandImageManager.TYPE_DISABLED, null, icon);
						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
								CommandImageManager.TYPE_HOVER, null, icon);
					}

				}
			}
		}
