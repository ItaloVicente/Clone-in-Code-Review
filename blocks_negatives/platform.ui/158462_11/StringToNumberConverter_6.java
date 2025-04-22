		ParseResult result = StringToNumberParser.parse(fromObject,
				numberFormat, toType.isPrimitive());

		if (result.getPosition() != null) {
			throw new IllegalArgumentException(StringToNumberParser
					.createParseErrorMessage((String) fromObject, result
							.getPosition()));
		} else if (result.getNumber() == null) {
			return null;
		}

		/*
		 * Technically the checks for ranges aren't needed here because the
		 * validator should have validated this already but we shouldn't assume
		 * this has occurred.
		 */
		if (Integer.class.equals(boxedType)) {
			if (StringToNumberParser.inIntegerRange(result.getNumber())) {
				return (T) Integer.valueOf(result.getNumber().intValue());
			}
		} else if (Double.class.equals(boxedType)) {
			if (StringToNumberParser.inDoubleRange(result.getNumber())) {
				return (T) Double.valueOf(result.getNumber().doubleValue());
			}
		} else if (Long.class.equals(boxedType)) {
			if (StringToNumberParser.inLongRange(result.getNumber())) {
				return (T) Long.valueOf(result.getNumber().longValue());
			}
		} else if (Float.class.equals(boxedType)) {
			if (StringToNumberParser.inFloatRange(result.getNumber())) {
				return (T) Float.valueOf(result.getNumber().floatValue());
			}
		} else if (BigInteger.class.equals(boxedType)) {
			Number n = result.getNumber();
			if(n instanceof Long)
				return (T) BigInteger.valueOf(n.longValue());
			else if(n instanceof BigInteger)
				return (T) n;
			else if(n instanceof BigDecimal)
				return (T) ((BigDecimal) n).toBigInteger();
			else
				return (T) BigDecimal.valueOf(n.doubleValue()).toBigInteger();
		} else if (BigDecimal.class.equals(boxedType)) {
			Number n = result.getNumber();
			if(n instanceof Long)
				return (T) BigDecimal.valueOf(n.longValue());
			else if(n instanceof BigInteger)
				return (T) new BigDecimal((BigInteger) n);
			else if(n instanceof BigDecimal)
				return (T) n;
			else if(icuBigDecimal != null && icuBigDecimal.isInstance(n)) {
				try {
					int scale = ((Integer) icuBigDecimalScale.invoke(n)).intValue();
					BigInteger unscaledValue = (BigInteger) icuBigDecimalUnscaledValue.invoke(n);
					return (T) new java.math.BigDecimal(unscaledValue, scale);
				} catch(IllegalAccessException e) {
				} catch(InvocationTargetException e) {
				}
			} else if(n instanceof Double) {
				BigDecimal bd = BigDecimal.valueOf(n.doubleValue());
				if (bd.scale() == 0)
					return (T) bd;
			}
		} else if (Short.class.equals(boxedType)) {
			if (StringToNumberParser.inShortRange(result.getNumber())) {
				return (T) Short.valueOf(result.getNumber().shortValue());
			}
		} else if (Byte.class.equals(boxedType)) {
			if (StringToNumberParser.inByteRange(result.getNumber())) {
				return (T) Byte.valueOf(result.getNumber().byteValue());
			}
		}

		if (min != null && max != null) {
			throw new IllegalArgumentException(StringToNumberParser
					.createOutOfRangeMessage(min, max, numberFormat));
		}

		/*
		 * Fail safe. I don't think this could even be thrown but throwing the
		 * exception is better than returning null and hiding the error.
		 */
		throw new IllegalArgumentException(
