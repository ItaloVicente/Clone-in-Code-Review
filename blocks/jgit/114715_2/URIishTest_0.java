
	@Test
	public void testEqualsHashcode() throws Exception
	{
				"\\\\some\\place"
				"user@example.com:some/p ath"
		for (String s : urls) {
			URIish u = new URIish(s);
			URIish v = new URIish(s);
			assertTrue(u.equals(v));
			assertTrue(v.equals(u));

			assertFalse(u.equals(null));
			assertFalse(u.equals(new Object()));
			assertFalse(new Object().equals(u));
			assertFalse(u.equals(w));
			assertFalse(w.equals(u));

			assertTrue(u.hashCode() == v.hashCode());
			assertFalse(u.hashCode() == new Object().hashCode());
		}
	}
