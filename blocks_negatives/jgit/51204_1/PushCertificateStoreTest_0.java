		assertEquals(expected.length, actual.size());
		for (int i = 0; i < expected.length; i++) {
			assertEquals("text of cert " + i,
					expected[i].toText(), actual.get(i).toText());
			assertEquals("signature of cert " + i,
					expected[i].getSignature(), actual.get(i).getSignature());
		}
