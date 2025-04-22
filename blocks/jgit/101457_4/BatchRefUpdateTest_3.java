
	private Map<String
			throws IOException {
		Map<String
		for (String name : names) {
			ReflogEntry e = getLastReflog(name);
			if (e != null) {
				result.put(name
			}
		}
		return result;
	}

	private ReflogEntry getLastReflog(String name) throws IOException {
		ReflogReader r = diskRepo.getReflogReader(name);
		if (r == null) {
			return null;
		}
		return r.getLastEntry();
	}

	private void assertReflogUnchanged(
			Map<String
		assertReflogEquals(old.get(name)
	}

	private static void assertReflogEquals(
			ReflogEntry expected
		assertReflogEquals(expected
	}

	private static void assertReflogEquals(
			ReflogEntry expected
		if (expected == null) {
			assertNull(actual);
			return;
		}
		assertNotNull(actual);
		assertEquals(expected.getOldId()
		assertEquals(expected.getNewId()
		if (strictTime) {
			assertEquals(expected.getWho()
		} else {
			assertEquals(expected.getWho().getName()
			assertEquals(
					expected.getWho().getEmailAddress()
					actual.getWho().getEmailAddress());
		}
		assertEquals(expected.getComment()
	}

	private static ReflogEntry reflog(ObjectId oldId
			PersonIdent who
		return new ReflogEntry() {
			@Override
			public ObjectId getOldId() {
				return oldId;
			}

			@Override
			public ObjectId getNewId() {
				return newId;
			}

			@Override
			public PersonIdent getWho() {
				return who;
			}

			@Override
			public String getComment() {
				return comment;
			}

			@Override
			public CheckoutEntry parseCheckout() {
				throw new UnsupportedOperationException();
			}
		};
	}
