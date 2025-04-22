
		@Override
		public Image getImage(Object element) {
			IContentType contentType = (IContentType) element;
			if (contentType.isUserDefined()) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_SAVE_EDIT);
			}
			return super.getImage(element);
		}
