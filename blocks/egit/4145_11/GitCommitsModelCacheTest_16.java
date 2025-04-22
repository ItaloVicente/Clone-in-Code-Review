		commonFileAssertations(actual, parent, change, name);
		if (direction == Differencer.LEFT) {
			assertThat(change.getRemoteCommitId(), not(ZERO_ID));
			assertThat(change.getObjectId(), nullValue());
			assertThat(change.getKind(), is(LEFT | ADDITION));
		} else { // should be Differencer.RIGHT
			assertThat(change.getObjectId(), not(ZERO_ID));
			assertThat(change.getRemoteObjectId(), nullValue());
			assertThat(change.getKind(), is(RIGHT | ADDITION));
		}
