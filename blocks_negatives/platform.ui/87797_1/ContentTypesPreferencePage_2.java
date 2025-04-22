
		@Override
		public Image getImage(Object element) {
			IContentType contentType = (IContentType) element;
			if (contentType.isUserDefined()) {
				return this.silhouette;
			}
			return super.getImage(element);
		}

		@Override
		public void dispose() {
			this.silhouette.dispose();
			super.dispose();
		}
