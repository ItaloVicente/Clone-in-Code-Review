			testPostalCode = postalCodeBuffer.toString();

			if (testPostalCode.length() != 6) {
			}

			if (testPostalCode.charAt(1) < '0'
					|| testPostalCode.charAt(1) > '9'
					|| testPostalCode.charAt(3) < '0'
					|| testPostalCode.charAt(3) > '9'
					|| testPostalCode.charAt(5) < '0'
					|| testPostalCode.charAt(5) > '9'
					|| testPostalCode.charAt(0) < 'A'
					|| testPostalCode.charAt(0) > 'Z'
					|| testPostalCode.charAt(2) < 'A'
					|| testPostalCode.charAt(2) > 'Z'
					|| testPostalCode.charAt(4) < 'A'
					|| testPostalCode.charAt(4) > 'Z') {
				return MessageUtil
						.format(
								"_is_an_invalid_format_for_a_postal_code", new Object[] { testPostalCode }); //$NON-NLS-1$
			}

			return null;
