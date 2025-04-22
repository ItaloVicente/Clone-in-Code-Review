	@Test
	public void testDonePruneTooYoungPacks() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/master");
		bb.commit().message("M").add("M"

		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(3
		assertEquals(1
		File oldPackfile = tr.getRepository().getObjectDatabase().getPacks()
				.iterator().next().getPackFile();

		fsTick();
		bb.commit().message("B").add("B"

		gc.setExpire(new Date(oldPackfile.lastModified() - 1));
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(9
		assertEquals(2

		gc.setExpireAgeMillis(0);
		gc.gc();
		stats = gc.getStatistics();
		assertEquals(0
		assertEquals(6
		assertEquals(1

	}

