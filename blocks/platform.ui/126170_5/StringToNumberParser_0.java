			double doubleValue = number.doubleValue();

			if (!Double.isNaN(doubleValue) && !Double.isInfinite(doubleValue)) {
				bigDecimal = new BigDecimal(doubleValue);
			} else {
				return false;
			}
