		Iterator<PackFile> pIt = repo.getObjectDatabase().getPacks().iterator();
		long c = pIt.next().getObjectCount();
		if (c == 9)
			assertEquals(2, pIt.next().getObjectCount());
		else {
			assertEquals(2, c);
			assertEquals(9, pIt.next().getObjectCount());
		}
