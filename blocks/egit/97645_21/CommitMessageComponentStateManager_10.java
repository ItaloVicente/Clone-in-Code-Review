		if (values.length == MEMBER_COUNT) {
			state.setCaretPosition(Integer.parseInt(values[3]));
			state.setCommitter(values[4]);
			state.setHeadCommit(ObjectId.fromString(values[5]));
		} else if (values.length == MEMBER_COUNT_WITHOUT_CARET_POSITION) {
			state.setCaretPosition(
					CommitMessageComponentState.CARET_DEFAULT_POSITION);
			state.setCommitter(values[3]);
			state.setHeadCommit(ObjectId.fromString(values[4]));
		} else {
			return null;
		}
