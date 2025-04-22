		propertyDescriptor.setValidator(value -> {
			if (value == null)
				return MessageUtil.getString("postal_code_is_incomplete"); //$NON-NLS-1$

			String testPostalCode = ((String) value).toUpperCase();
			final int length = testPostalCode.length();
			final char space = ' ';

			StringBuilder postalCodeBuffer = new StringBuilder(6);
			char current;
			for (int i = 0; i < length; i++) {
				current = testPostalCode.charAt(i);
				if (current != space)
					postalCodeBuffer.append(current);
