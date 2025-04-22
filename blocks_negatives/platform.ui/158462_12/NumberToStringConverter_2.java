		if (fromObject == null && !fromType.isPrimitive()) {
		}

		Number number = (Number) fromObject;
		String result = null;
		if (fromTypeFitsLong) {
			synchronized (numberFormat) {
				result = numberFormat.format(number.longValue());
			}
		} else if (fromTypeIsDecimalType) {
			synchronized (numberFormat) {
				result = numberFormat.format(number.doubleValue());
			}
		} else if (fromTypeIsBigInteger) {
			synchronized (numberFormat) {
				result = numberFormat.format((BigInteger) number);
			}
		} else if (fromTypeIsBigDecimal) {
			if(icuBigDecimal != null && icuBigDecimalCtr != null && numberFormat instanceof DecimalFormat) {
				BigDecimal o = (BigDecimal) fromObject;
				try {
					fromObject = icuBigDecimalCtr.newInstance(o.unscaledValue(), Integer.valueOf(o.scale()));
				}
				catch(InstantiationException | InvocationTargetException | IllegalAccessException e) {}
			}
			synchronized (numberFormat) {
				result = numberFormat.format(fromObject);
			}
		}


		return result;
