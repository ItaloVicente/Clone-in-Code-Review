	public static boolean waitForCondition(BooleanSupplier condition, Display display, long timeout) {
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

