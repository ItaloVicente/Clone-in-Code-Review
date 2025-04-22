		Object typedValue = value;
		boolean oldValidState = isValueValid();
		boolean newValidState = isCorrect(typedValue);
		if (!newValidState) {
			setErrorMessage(MessageFormat.format(getErrorMessage(), value));
		}
		valueChanged(oldValidState, newValidState);
	}

	@Override
