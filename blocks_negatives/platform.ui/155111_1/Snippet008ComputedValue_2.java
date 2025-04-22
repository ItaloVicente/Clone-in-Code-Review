	/**
	 * Creates the formatted name on change of the first or last name observables.
	 * <p>
	 * The key to understanding ComputedValue is understanding that it knows of the
	 * observables that are queried without being told. This is done with
	 * {@link ObservableTracker} voodoo. When calculate() is invoked
	 * <code>ObservableTracker</code> records the observables that are queried. It
	 * then exposes those observables and <code>ComputedValue</code> can listen to
	 * changes in those objects and react accordingly.
	 */
	static class FormattedName extends ComputedValue<String> {
		private IObservableValue<String> firstName;

		private IObservableValue<String> lastName;

		FormattedName(IObservableValue<String> firstName, IObservableValue<String> lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		@Override
		protected String calculate() {
			String lastName = this.lastName.getValue();
			String firstName = this.firstName.getValue();
			lastName = (lastName != null && lastName.length() > 0) ? lastName : "[Last Name]";
			firstName = (firstName != null && firstName.length() > 0) ? firstName : "[First Name]";

			StringBuilder buffer = new StringBuilder();
			buffer.append(lastName).append(", ").append(firstName);

			return buffer.toString();
		}
	}
