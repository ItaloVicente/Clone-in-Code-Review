	@Test
	public void testGetRefs_IgnoresGarbageRef5() throws IOException {
		Map<String
		Ref a;

		writeLooseRef("refs/heads/A"
		write(new File(diskRepo.getDirectory()
				"FAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAIL\n");

		heads = refdir.getRefs(RefDatabase.ALL);
		assertEquals(1

		a = heads.get("refs/heads/A");
		assertEquals("refs/heads/A"
		assertEquals(A
	}

