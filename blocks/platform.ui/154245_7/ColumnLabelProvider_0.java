	public static ColumnLabelProvider createTextProvider(Function<Object, String> textFunction) {
		Objects.requireNonNull(textFunction);
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object e) {
				return textFunction.apply(e);
			}
		};
	}

	public static ColumnLabelProvider createImageProvider(Function<Object, Image> imageFunction) {
		Objects.requireNonNull(imageFunction);
		return new ColumnLabelProvider() {
			@Override
			public Image getImage(Object e) {
				return imageFunction.apply(e);
			}
		};
	}

	public static ColumnLabelProvider createTextImageProvider(Function<Object, String> textFunction,
			Function<Object, Image> imageFunction) {
		Objects.requireNonNull(textFunction);
		Objects.requireNonNull(imageFunction);
		return new ColumnLabelProvider() {
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

