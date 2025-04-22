	@Test
	public void testFirstExactRef_IgnoresGarbageRef() throws IOException {
		writeLooseRef("refs/heads/A"
		write(new File(diskRepo.getDirectory()

		Ref a = refdir.firstExactRef("refs/heads/bad"
		assertEquals("refs/heads/A"
		assertEquals(A
	}

	@Test
	public void testExactRef_IgnoresGarbageRef() throws IOException {
		writeLooseRef("refs/heads/A"
		write(new File(diskRepo.getDirectory()

		Map<String
				refdir.exactRef("refs/heads/bad"

		assertNull("no refs/heads/bad"

		Ref a = refs.get("refs/heads/A");
		assertEquals("refs/heads/A"
		assertEquals(A

		assertEquals(1
	}

