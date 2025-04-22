	@Test
	public void testInvalidWant() throws Exception {
		@SuppressWarnings("resource")
		ObjectId id = new ObjectInserter.Formatter().idFor(Constants.OBJ_BLOB
				"testInvalidWant".getBytes(StandardCharsets.UTF_8));

		Repository dst = createBareRepository();
		try (Transport t = Transport.open(dst
				FetchConnection c = t.openFetch()) {
			Ref want = new ObjectIdRef.Unpeeled(Ref.Storage.NETWORK
					id);
			c.fetch(NullProgressMonitor.INSTANCE
					Collections.<ObjectId> emptySet());
			fail("Server accepted want " + id.name());
		} catch (TransportException err) {
			assertEquals("want " + id.name() + " not valid"
		}
	}

