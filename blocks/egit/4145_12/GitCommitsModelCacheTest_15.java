		assertThat(commit.getAuthorName(), is(actualCommit.getAuthorIdent()
				.getName()));
		assertThat(commit.getCommitterName(), is(actualCommit
				.getCommitterIdent().getName()));
		assertThat(commit.getCommitDate(), is(actualCommit.getAuthorIdent()
				.getWhen()));
