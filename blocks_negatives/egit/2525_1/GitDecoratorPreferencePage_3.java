		public String getText(Object element) {
			final PreviewDecoration decoration = getDecoration(element);
			final StringBuilder buffer = new StringBuilder();
			final String prefix = decoration.getPrefix();
			if (prefix != null)
				buffer.append(prefix);
			buffer.append(((PreviewResource) element).getName());
			final String suffix = decoration.getSuffix();
			if (suffix != null)
				buffer.append(suffix);
			return buffer.toString();
		}

		public Image getImage(Object element) {
			final String s;
			switch (((PreviewResource) element).type) {
			case IResource.PROJECT:
				s = SharedImages.IMG_OBJ_PROJECT;
				break;
			case IResource.FOLDER:
				s = ISharedImages.IMG_OBJ_FOLDER;
				break;
			default:
				s = ISharedImages.IMG_OBJ_FILE;
				break;
			}
			final Image baseImage = PlatformUI.getWorkbench().getSharedImages()
					.getImage(s);
			final ImageDescriptor overlay = getDecoration(element).getOverlay();
			if (overlay == null)
				return baseImage;
			try {
				return fImageCache.createImage(new DecorationOverlayIcon(
						baseImage, overlay, IDecoration.BOTTOM_RIGHT));
			} catch (Exception e) {
				Activator.logError(e.getMessage(), e);
			}

			return null;
		}

