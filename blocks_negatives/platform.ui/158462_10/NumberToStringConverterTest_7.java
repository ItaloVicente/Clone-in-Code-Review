	/**
	 * Takes a java.math.BigDecimal and returns an ICU formatted string for it,
	 * when ICU is available, otherwise platform default. Note that
	 * Java &lt; 1.5 did not format BigDecimals properly, truncating them via doubleValue(),
	 * so this method will return bad results, Data Binding will not, so
	 * the test will FAIL on Java &lt; 1.5 under these conditions.
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
