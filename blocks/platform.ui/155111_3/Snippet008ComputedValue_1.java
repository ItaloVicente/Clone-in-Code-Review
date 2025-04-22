		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(firstName), firstNameValue);
		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(lastName), lastNameValue);

		ComputedValue<String> name = new ComputedValue<String>() {
			@Override
			protected String calculate() {
				String lastName = lastNameValue.getValue();
				String firstName = firstNameValue.getValue();
				String lastNameOrDefault = lastName == null || lastName.isEmpty() ? "[Last Name]" : lastName;
				String firstNameOrDefault = firstName == null || firstName.isEmpty() ? "[First Name]" : firstName;

				return lastNameOrDefault + ", " + firstNameOrDefault;
			}
		};
