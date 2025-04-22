
	private void assertCorrupt(String msg
		assertCorrupt(msg
	}

	private void assertCorrupt(String msg
		try {
			checker.check(type
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException e) {
			assertEquals(msg
		}
	}

	private void assertSkipListAccepts(int type
			throws CorruptObjectException {
		ObjectId id = idFor(type
		checker.setSkipList(set(id));
		checker.check(id
		checker.setSkipList(null);
	}

	private static ObjectIdSet set(final ObjectId... ids) {
		return new ObjectIdSet() {
			@Override
			public boolean contains(AnyObjectId objectId) {
				for (ObjectId id : ids) {
					if (id.equals(objectId)) {
						return true;
					}
				}
				return false;
			}
		};
	}

	@SuppressWarnings("resource")
	private static ObjectId idFor(int type
		return new ObjectInserter.Formatter().idFor(type
	}
