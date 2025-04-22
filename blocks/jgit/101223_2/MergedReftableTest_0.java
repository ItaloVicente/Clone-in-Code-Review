	@Test
	public void compaction() throws IOException {
		List<Ref> delta1 = Arrays.asList(
				ref("refs/heads/next"
				ref("refs/heads/master"
		List<Ref> delta2 = Arrays.asList(delete("refs/heads/next"));
		List<Ref> delta3 = Arrays.asList(ref("refs/heads/master"

		ReftableCompactor rc = new ReftableCompactor();
		rc.addAll(Arrays.asList(
				read(write(delta1))
				read(write(delta2))
				read(write(delta3))));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		rc.compact(out);
		byte[] table = out.toByteArray();

		ReftableReader rr = read(table);
		rr.seekToFirstRef();
		assertTrue(rr.next());
		Ref r = rr.getRef();
		assertEquals("refs/heads/master"
		assertEquals(id(8)

		assertFalse(rr.next());
	}

