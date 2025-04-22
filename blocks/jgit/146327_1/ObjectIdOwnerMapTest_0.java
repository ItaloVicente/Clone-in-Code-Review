	@Test
	public void testGetWithNullObject_doesNotThrowNPE() {
		ObjectIdOwnerMap<SubId> m = new ObjectIdOwnerMap<>();

		SubId result = m.get(null);

		assertNull(result);
	}

