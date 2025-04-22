	@Test
	public void compaction() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/next"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(delete("refs/heads/next"));
		List<Ref> delta3 = Arrays.asList(ref("refs/heads/master"

		ReftableCompactor compactor = new ReftableCompactor();
		compactor.addAll(Arrays.asList(
				read(write(delta1))
				read(write(delta2))
				read(write(delta3))));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		compactor.compact(out);
		byte[] table = out.toByteArray();

		ReftableReader reader = read(table);
		try (RefCursor rc = reader.allRefs()) {
			assertTrue(rc.next());
			Ref r = rc.getRef();
			assertEquals("refs/heads/master"
			assertEquals(id(8)
			assertFalse(rc.next());
		}
	}

