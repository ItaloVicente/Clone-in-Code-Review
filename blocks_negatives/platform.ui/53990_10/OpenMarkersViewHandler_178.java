		return new IInputValidator() {
			@Override
			public String isValid(String newText) {
				if (newText.length() > 0)
					return null;
				return MarkerMessages.MarkerFilterDialog_emptyMessage;
			}
