		IConverter<String, String> upperCaseConverter = new IConverter<>() {
			@Override
			public String convert(String fromObject) {
				return fromObject.toUpperCase();
			}

			@Override
			public Object getFromType() {
				return String.class;
			}
