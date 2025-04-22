		commonFileAssertations(actual, parent, change, name);
		if (direction == LEFT) {
			assertThat(change.getRemoteObjectId(), nullValue());
			assertThat(change.getObjectId(), not(ZERO_ID));
			assertThat(change.getKind(), is(LEFT | DELETION));
		} else { // should be Differencer.Right
			assertThat(change.getKind(), is(RIGHT | DELETION));
			assertThat(change.getObjectId(), nullValue());
			assertThat(change.getRemoteObjectId(), not(ZERO_ID));
		}
