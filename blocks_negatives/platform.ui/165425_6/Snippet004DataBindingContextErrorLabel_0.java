
	/**
	 * Validator that returns validation errors for any value other than 5.
	 */
	private static class FiveValidator implements IValidator<String> {
		@Override
		public IStatus validate(String value) {
			return ("5".equals(value)) ? Status.OK_STATUS
					: ValidationStatus.error("the value was '" + value + "', not '5'");
		}
	}
