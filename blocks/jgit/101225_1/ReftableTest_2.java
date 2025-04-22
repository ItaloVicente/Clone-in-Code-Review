	@SuppressWarnings("boxing")
	@Test
	public void seekFromList() throws IOException {
		List<Ref> refs = new ArrayList<>();
		for (int i = 1; i <= 567; i++) {
			refs.add(ref(String.format("refs/heads/%03d"
		}

		for (Ref exp : refs) {
			RefCursor r = RefCursor.from(refs);
			r.seek(exp.getName());
			assertTrue("has " + exp.getName()
			Ref act = r.getRef();
			assertEquals(exp.getName()
			assertEquals(exp.getObjectId()
			assertFalse(r.next());
		}
	}

