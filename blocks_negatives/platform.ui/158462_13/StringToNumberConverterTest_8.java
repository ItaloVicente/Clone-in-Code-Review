	/**
	 * Takes a java.math.BigDecimal and returns an ICU formatted string for it.
	 * These tests depend on ICU to reliably format test strings for comparison.
	 * Java &lt; 1.5 DecimalFormat did not format/parse BigDecimals properly,
	 * converting them via doubleValue(), so we have a dependency for this unit test on ICU4J.
	 * See Bug #180392 for more info.
	 * @param bd
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	private String formatBigDecimal(BigDecimal javabd) throws Exception {
		if(icuBigDecimal != null && icuBigDecimalCtr != null) {
			Number icubd = (Number) icuBigDecimalCtr.newInstance(javabd.unscaledValue(),
					Integer.valueOf(javabd.scale()));
			return numberFormat.format(icubd);
		}
		throw new IllegalArgumentException("ICU not present. Cannot reliably format large BigDecimal values; needed for testing. Java platforms prior to 1.5 fail to format/parse these decimals correctly.");
	}
