public class NumberToStringConverter extends Converter<Object, String> {
	private final NumberFormat numberFormat;
	private final Class<?> fromType;
	private boolean fromTypeFitsLong;
	private boolean fromTypeIsDecimalType;
	private boolean fromTypeIsBigInteger;
	private boolean fromTypeIsBigDecimal;

	static Class<?> icuBigDecimal = null;
	static Constructor<?> icuBigDecimalCtr = null;

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
			icuBigDecimalCtr = icuBigDecimal.getConstructor(BigInteger.class, int.class);
		}
		catch(ClassNotFoundException | NoSuchMethodException e) {}
	}

	/**
	 * Constructs a new instance.
	 * <p>
	 * Private to restrict public instantiation.
	 * </p>
	 *
	 * @param numberFormat used to format the numbers into strings. Non-null.
	 * @param fromType     type of the source numbers. Non-null.
	 */
	private NumberToStringConverter(NumberFormat numberFormat, Class<?> fromType) {
		super(fromType, String.class);

		this.numberFormat = Objects.requireNonNull(numberFormat);
		this.fromType = Objects.requireNonNull(fromType);

		if (Integer.class.equals(fromType) || Integer.TYPE.equals(fromType)
				|| Long.class.equals(fromType) || Long.TYPE.equals(fromType)
				|| Short.class.equals(fromType) || Short.TYPE.equals(fromType)
				|| Byte.class.equals(fromType) || Byte.TYPE.equals(fromType)) {
			fromTypeFitsLong = true;
		} else if (Float.class.equals(fromType) || Float.TYPE.equals(fromType)
				|| Double.class.equals(fromType)
				|| Double.TYPE.equals(fromType)) {
			fromTypeIsDecimalType = true;
		} else if (BigInteger.class.equals(fromType)) {
			fromTypeIsBigInteger = true;
		} else if (BigDecimal.class.equals(fromType)) {
			fromTypeIsBigDecimal = true;
		}
