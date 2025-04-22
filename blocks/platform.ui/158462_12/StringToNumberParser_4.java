	private static final Supplier<Format> GET_INSTANCE = findMethod(NumberFormat::getInstance, "getInstance"); //$NON-NLS-1$
	private static final Supplier<Format> GET_NUMBER_INSTANCE = findMethod(NumberFormat::getNumberInstance,
			"getNumberInstance"); //$NON-NLS-1$
	private static final Supplier<Format> GET_INTEGER_INSTANCE = findMethod(NumberFormat::getIntegerInstance,
			"getIntegerInstance"); //$NON-NLS-1$

