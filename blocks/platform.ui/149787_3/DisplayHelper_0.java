	public static boolean waitForCondition(Display display, long timeout, BooleanSupplier condition) {
		if (condition == null) {
			throw new IllegalArgumentException("condition mustn't be null");
		}
		return new DisplayHelper() {
			@Override
			protected boolean condition() {
				return condition.getAsBoolean();
			}
		}.waitForCondition(display, timeout);
	}

