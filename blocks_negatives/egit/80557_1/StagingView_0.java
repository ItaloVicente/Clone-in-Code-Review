		public Image getColumnImage(Object element) {
			Image image = provider.getImage(element);
			if (image != null && decorator != null) {
				Image decorated = decorator.decorateImage(image, element);
				if (decorated != null)
					return decorated;
			}
			return image;
