		if (values.length == MEMBER_COUNT) {
			state.setCommitter(values[3]);
			state.setHeadCommit(ObjectId.fromString(values[4]));
			state.setCaretPosition(Integer.parseInt(values[5]));
		} else if (values.length == MEMBER_COUNT_WITHOUT_CARET_POSITION) {
			state.setCommitter(values[3]);
			state.setHeadCommit(ObjectId.fromString(values[4]));
			state.setCaretPosition(
					CommitMessageComponentState.CARET_DEFAULT_POSITION);
		} else {
			return null;
		}
