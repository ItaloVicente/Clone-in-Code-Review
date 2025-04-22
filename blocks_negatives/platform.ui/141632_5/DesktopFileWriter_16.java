		Function<Entry<String, String>, String> toList = new Function<Map.Entry<String, String>, String>() {

			@Override
			public String apply(Entry<String, String> e) {
				if (e.getValue() == null) {
					return e.getKey();
				}
				return String.join(EQUAL_SIGN, e.getKey(), e.getValue());
