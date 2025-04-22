
			@Override
			public void checkCommit(AnyObjectId id
					throws CorruptObjectException {
				throw new IllegalStateException();
			}
