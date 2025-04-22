	public void assertIndex(HashMap<String, String> i)
			throws CorruptObjectException, IOException {
		String expectedValue;
		String path;
		GitIndex theIndex=db.getIndex();
		theIndex.read();
		assertEquals("Index has not the right size.", i.size(),
				theIndex.getMembers().length);
		for (int j = 0; j < theIndex.getMembers().length; j++) {
			path = theIndex.getMembers()[j].getName();
			expectedValue = i.get(path);
			assertNotNull("found unexpected entry for path " + path
					+ " in index", expectedValue);
			assertTrue("unexpected content for path " + path
					+ " in index. Expected: <" + expectedValue + ">",
					Arrays.equals(
							db.open(theIndex.getMembers()[j].getObjectId())
									.getCachedBytes(), i.get(path).getBytes()));
		}
	}
