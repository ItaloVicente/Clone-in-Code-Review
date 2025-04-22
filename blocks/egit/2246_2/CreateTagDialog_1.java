		private TagLabelProvider() {
			fImageCache = new LocalResourceManager(
					JFaceResources.getResources());
			IMG_TAG = fImageCache.createImage(UIIcons.TAG);
			IMG_LIGHTTAG = SWTUtils.getDecoratedImage(
					fImageCache.createImage(UIIcons.TAG), UIIcons.OVR_LIGHTTAG);
		}
