		modelToTarget.setConverter(new IConverter<String, String>() {

			@Override
			public Object getFromType() {
				return String.class;
			}

			@Override
			public Object getToType() {
				return String.class;
			}

			@Override
			public String convert(String fromObject) {
				throw new IllegalArgumentException();
			}

		});
