		for (String encoding : encodings) {
			try {
				if (!Charset.isSupported(encoding)) {
					invalid.add(encoding);
				}
			} catch (IllegalCharsetNameException e) {
				invalid.add(encoding);
