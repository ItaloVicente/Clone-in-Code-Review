
	public static IConverter create(Object fromType, Object toType, @SuppressWarnings("rawtypes") Function conversion) {
		return new IConverter() {

			@Override
			public Object getFromType() {
				return fromType;
			}

			@Override
			public Object getToType() {
				return toType;
			}

			@SuppressWarnings("unchecked")
			@Override
			public Object convert(Object fromObject) {
				return conversion.apply(fromObject);
			}
		};
	}
