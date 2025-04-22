public class StringToNumberConverter<T extends Number> extends NumberFormatConverter<Object, T> {
	private Class<?> toType;
	/**
	 * NumberFormat instance to use for conversion. Access must be synchronized.
	 */
	private NumberFormat numberFormat;

	/**
	 * Minimum possible value for the type. Can be <code>null</code> as
	 * BigInteger doesn't have bounds.
	 */
	private final Number min;
	/**
	 * Maximum possible value for the type. Can be <code>null</code> as
	 * BigInteger doesn't have bounds.
	 */
	private final Number max;

	/**
	 * The boxed type of the toType;
	 */
	private final Class<?> boxedType;

	private static final Integer MIN_INTEGER = Integer.valueOf(Integer.MIN_VALUE);
	private static final Integer MAX_INTEGER = Integer.valueOf(Integer.MAX_VALUE);

	private static final Double MIN_DOUBLE = Double.valueOf(-Double.MAX_VALUE);
	private static final Double MAX_DOUBLE = Double.valueOf(Double.MAX_VALUE);

	private static final Long MIN_LONG = Long.valueOf(Long.MIN_VALUE);
	private static final Long MAX_LONG = Long.valueOf(Long.MAX_VALUE);

	private static final Float MIN_FLOAT = Float.valueOf(-Float.MAX_VALUE);
	private static final Float MAX_FLOAT = Float.valueOf(Float.MAX_VALUE);

	private static final Short MIN_SHORT = Short.valueOf(Short.MIN_VALUE);
	private static final Short MAX_SHORT = Short.valueOf(Short.MAX_VALUE);

	private static final Byte MIN_BYTE = Byte.valueOf(Byte.MIN_VALUE);
	private static final Byte MAX_BYTE = Byte.valueOf(Byte.MAX_VALUE);

	static Class<?> icuBigDecimal = null;
	static Method icuBigDecimalScale = null;
	static Method icuBigDecimalUnscaledValue = null;

	{
		/*
		 * If the full ICU4J library is available, we use the ICU BigDecimal
		 * class to support proper formatting and parsing of java.math.BigDecimal.
		 *
		 * The version of ICU NumberFormat (DecimalFormat) included in eclipse excludes
		 * support for java.math.BigDecimal, and if used falls back to converting as
		 * an unknown Number type via doubleValue(), which is undesirable.
		 *
		 * See Bug #180392.
		 */
		try {
					(icuBigDecimal != null)+", icuBigDecimalScale="+(icuBigDecimalScale != null)+ //$NON-NLS-1$
					", icuBigDecimalUnscaledValue="+(icuBigDecimalUnscaledValue != null)); //$NON-NLS-1$ */
		}
		catch(ClassNotFoundException | NoSuchMethodException e) {}
	}

