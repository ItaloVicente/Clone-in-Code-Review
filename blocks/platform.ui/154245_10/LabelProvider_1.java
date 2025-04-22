
	public static LabelProvider createTextProvider(Function<Object, String> textFunction) {
		Objects.requireNonNull(textFunction);
		return new LabelProvider() {
			@Override
			public String getText(Object e) {
				return textFunction.apply(e);
			}
		};
	}

	public static LabelProvider createImageProvider(Function<Object, Image> imageFunction) {
		Objects.requireNonNull(imageFunction);
		return new LabelProvider() {
			@Override
			public Image getImage(Object e) {
				return imageFunction.apply(e);
			}
		};
	}

	public static LabelProvider createTextImageProvider(Function<Object, String> textFunction,
			Function<Object, Image> imageFunction) {
		Objects.requireNonNull(textFunction);
		Objects.requireNonNull(imageFunction);
		return new LabelProvider() {
			@Override
			public String getText(Object e) {
				return textFunction.apply(e);
			}
			@Override
			public Image getImage(Object e) {
				return imageFunction.apply(e);
			}
		};
	}

