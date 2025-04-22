		Commit mockCommit1 = mock(Commit.class);
		when(mockCommit1.getCommitTime()).thenReturn(10);
		Commit mockCommit2 = mock(Commit.class);
		when(mockCommit2.getCommitTime()).thenReturn(20);
		when(commit1.getCachedCommitObj()).thenReturn(mockCommit1);
		when(commit2.getCachedCommitObj()).thenReturn(mockCommit2);
