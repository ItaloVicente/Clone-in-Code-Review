		assertEquals("WorkDir has not the right size."
	}


	public void assertIndex(HashMap<String
			throws CorruptObjectException
		String expectedValue;
		String path;
		GitIndex theIndex=db.getIndex();
		assertEquals("Index has not the right size."
				theIndex.getMembers().length);
		for (int j = 0; j < theIndex.getMembers().length; j++) {
			path = theIndex.getMembers()[j].getName();
			expectedValue = i.get(path);
			assertNotNull("found unexpected entry for path " + path
					+ " in index"
			assertTrue("unexpected content for path " + path
					+ " in index. Expected: <" + expectedValue + ">"
					Arrays.equals(
							db.openBlob(theIndex.getMembers()[j].getObjectId())
									.getBytes()
