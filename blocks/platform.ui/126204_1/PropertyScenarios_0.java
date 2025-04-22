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
