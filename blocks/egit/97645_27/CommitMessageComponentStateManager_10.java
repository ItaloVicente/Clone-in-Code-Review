		if (values.length == MEMBER_COUNT) {
			state.setCaretPosition(Integer.parseInt(values[5]));
		} else if (values.length == MEMBER_COUNT_WITHOUT_CARET_POSITION) {
			state.setCaretPosition(
					CommitMessageComponentState.CARET_DEFAULT_POSITION);
		}
