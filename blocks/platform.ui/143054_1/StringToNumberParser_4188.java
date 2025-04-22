			double doubleValue = number.doubleValue();

			if (!Double.isNaN(doubleValue) && !Double.isInfinite(doubleValue)) {
				bigDecimal = BigDecimal.valueOf(doubleValue);
			} else {
				return false;
			}
