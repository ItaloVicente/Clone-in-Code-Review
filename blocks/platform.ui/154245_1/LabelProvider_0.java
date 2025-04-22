
	static LabelProvider getTextAdapter(Function<Object, String> function) {
		return new LabelProvider() {
			@Override
			public String getText(Object e) {
				return function.apply(e);
			}
		};
	}

	static LabelProvider getImageAdapter(Function<Object, Image> function) {
		return new LabelProvider() {
			@Override
			public Image getImage(Object e) {
				return function.apply(e);
			}
		};
	}

