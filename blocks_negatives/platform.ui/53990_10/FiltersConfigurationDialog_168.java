		return new IInputValidator() {

			@Override
			public String isValid(String newText) {
				newText = newText.trim();
				if (newText.length() == 0) {
					return MarkerMessages.MarkerFilterDialog_emptyMessage;
				}
				if(existingNames.contains(newText) && !currentName.equals(newText)) {
					return NLS.bind(MarkerMessages.filtersDialog_conflictingName, newText);
				}
				return null;
