
	public static LabelProvider getTextAdapter(Function<Object, String> f) {
		return new LabelProvider() {
			@Override
			public String getText(Object e) {
				return f.apply(e);
			}
		};
	}

	public static LabelProvider getImageAdapter(Function<Object, Image> f) {
		return new LabelProvider() {
			@Override
			public Image getImage(Object e) {
				return f.apply(e);
			}
		};
	}

