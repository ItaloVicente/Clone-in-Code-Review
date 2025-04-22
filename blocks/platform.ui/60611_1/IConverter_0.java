
	public static IConverter create(Object fromType, Object toType, Function<Object, Object> conversion) {
		return new IConverter() {

			@Override
			public Object getFromType() {
				return fromType;
			}

			@Override
			public Object getToType() {
				return toType;
			}

			@Override
			public Object convert(Object fromObject) {
				return conversion.apply(fromObject);
			}
		};
	}
