		Text text = new Text(getComposite(), SWT.BORDER);
		final String noSpacesMessage = "Name must not contain spaces.";
		final String max15CharactersMessage = "Maximum length for name is 15 characters.";
		adventure.setName("ValidValue");

		IValidator validator = value -> {
			String stringValue = (String) value;
			if (stringValue.length() > 15) {
				return ValidationStatus.error(max15CharactersMessage);
			} else if (stringValue.indexOf(' ') != -1) {
				return ValidationStatus.cancel(noSpacesMessage);
			} else {
				return Status.OK_STATUS;
			}
		};
