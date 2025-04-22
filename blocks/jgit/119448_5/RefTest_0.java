
	private static void checkContainsRef(List<Ref> haystack
		for (Ref ref : haystack) {
			if (ref.getName().equals(needle.getName()) &&
					ref.getObjectId().equals(needle.getObjectId())) {
				return;
			}
		}
		fail("list " + haystack + " does not contain ref " + needle);
	}

	@Test
	public void testGetRefsByPrefix() throws IOException {
		List<Ref> refs = db.getRefDatabase().getRefsByPrefix("refs/heads/g");
		assertEquals(2
		checkContainsRef(refs
		checkContainsRef(refs

		refs = db.getRefDatabase().getRefsByPrefix("refs/heads/prefix/");
		assertEquals(1
		checkContainsRef(refs
	}
