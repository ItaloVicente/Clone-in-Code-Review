		if(icuBigDecimal != null && icuBigDecimalCtr != null) {
			Number icubd = (Number) icuBigDecimalCtr.newInstance(javabd.unscaledValue(),
					Integer.valueOf(javabd.scale()));
			return numberFormat.format(icubd);
		}
		throw new IllegalArgumentException("ICU not present. Cannot reliably format large BigDecimal values; needed for testing. Java platforms prior to 1.5 fail to format/parse these decimals correctly.");
