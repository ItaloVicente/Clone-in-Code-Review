		assertThat(leftResult, notNullValue());
		assertThat(leftResult.size(), is(2));
		assertEmptyCommit(leftResult.get(0), c2, LEFT);
		assertEmptyCommit(leftResult.get(1), c1, LEFT);

		assertThat(rightResult, notNullValue());
		assertThat(rightResult.size(), is(2));
		assertEmptyCommit(rightResult.get(0), c2, RIGHT);
		assertEmptyCommit(rightResult.get(1), c1, RIGHT);
