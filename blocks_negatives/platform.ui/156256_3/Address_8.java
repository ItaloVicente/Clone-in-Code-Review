		propertyDescriptor.setValidator(value -> {
			if (value == null)

			String testPostalCode = ((String) value).toUpperCase();
			final int length = testPostalCode.length();
			final char space = ' ';

			StringBuilder postalCodeBuffer = new StringBuilder(6);
			char current;
			for (int i = 0; i < length; i++) {
				current = testPostalCode.charAt(i);
				if (current != space)
					postalCodeBuffer.append(current);
