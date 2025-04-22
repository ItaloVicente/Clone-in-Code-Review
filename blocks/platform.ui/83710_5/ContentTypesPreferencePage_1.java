
		@Override
		public Image getImage(Object element) {
			IContentType contentType = (IContentType) element;
			if (contentType.isUserDefined()) {
				return super.getImage(element);
			}
			return super.getImage(element);
		}
