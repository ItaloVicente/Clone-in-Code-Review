		Change change = mock(Change.class);
		when(change.getObjectId()).thenReturn(
				AbbreviatedObjectId.fromObjectId(cacheId));
		when(change.getRemoteObjectId()).thenReturn(
				AbbreviatedObjectId.fromObjectId(repoId));

