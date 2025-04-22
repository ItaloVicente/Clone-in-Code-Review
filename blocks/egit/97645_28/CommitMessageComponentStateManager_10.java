		if (values.length >= MEMBER_COUNT) {
			state.setCaretPosition(Integer.parseInt(values[5]));
		} else {
			state.setCaretPosition(
					CommitMessageComponentState.CARET_DEFAULT_POSITION);
		}
