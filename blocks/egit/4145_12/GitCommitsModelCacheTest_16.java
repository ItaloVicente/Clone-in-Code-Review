		commonFileAssertations(actual, parent, change, name);
		if (direction == Differencer.LEFT) {
			assertThat(change.getKind(), is(LEFT | ADDITION));
			assertThat(change.getRemoteCommitId(), not(ZERO_ID));
			assertThat(change.getObjectId(), nullValue());
		} else { // should be Differencer.Right
			assertThat(change.getKind(), is(RIGHT | ADDITION));
			assertThat(change.getObjectId(), not(ZERO_ID));
			assertThat(change.getRemoteObjectId(), nullValue());
		}
