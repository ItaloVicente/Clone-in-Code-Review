				boolean newValidState = isCorrect(newValue);
				if (newValidState) {
					markDirty();
					doSetValue(newValue);
				} else {
					setErrorMessage(MessageFormat.format(getErrorMessage(), newValue.toString()));
				}
				fireApplyEditorValue();
			}
