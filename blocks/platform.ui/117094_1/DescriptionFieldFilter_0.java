		} else {
			value = (String) values.get(MarkerSupportConstants.DOES_NOT_CONTAIN_KEY);
			if (value != null) {
				setContainsText(value);
				setContainsModifier(MarkerSupportConstants.DOES_NOT_CONTAIN_KEY);
			}
