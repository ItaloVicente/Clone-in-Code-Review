		Commit mockCommit1 = mock(Commit.class);
		Commit mockCommit2 = mock(Commit.class);
		when(mockCommit1.getCommitDate()).thenReturn(new Date(333333L));
		when(mockCommit2.getCommitDate()).thenReturn(new Date(555555L));
		when(commit1.getCachedCommitObj()).thenReturn(mockCommit1);
		when(commit2.getCachedCommitObj()).thenReturn(mockCommit2);
