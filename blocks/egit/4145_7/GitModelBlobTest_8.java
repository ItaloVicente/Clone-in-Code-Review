		Change change = mock(Change.class);
		if (baseId != null)
			when(change.getObjectId()).thenReturn(
					AbbreviatedObjectId.fromObjectId(baseId));
		if (remoteId != null)
			when(change.getRemoteObjectId()).thenReturn(
					AbbreviatedObjectId.fromObjectId(remoteId));

