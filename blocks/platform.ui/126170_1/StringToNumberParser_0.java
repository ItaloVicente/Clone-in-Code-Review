			double doubleValue = number.doubleValue();

			if (!Double.isNaN(doubleValue) && doubleValue != Double.NEGATIVE_INFINITY
					&& doubleValue != Double.POSITIVE_INFINITY) {
				bigDecimal = new BigDecimal(doubleValue);
			} else {
				return false;
			}
