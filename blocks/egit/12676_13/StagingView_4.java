	class TreeDecoratingLabelProvider extends DecoratingLabelProvider {

		ILabelProvider provider;

		ILabelDecorator decorator;

		public TreeDecoratingLabelProvider(ILabelProvider provider,
				ILabelDecorator decorator) {
			super(provider, decorator);
			this.provider = provider;
			this.decorator = decorator;
		}

		public Image getColumnImage(Object element) {
			Image image = provider.getImage(element);
			if (image != null && decorator != null) {
				Image decorated = decorator.decorateImage(image, element);
				if (decorated != null)
					return decorated;
			}
			return image;
		}

		public String getText(Object element) {
			return provider.getText(element);
		}
	}

