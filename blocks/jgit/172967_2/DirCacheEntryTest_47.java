	private static void checkPath(DirCacheVersion indexVersion
			DirCacheEntry previous
		DirCacheEntry dce = new DirCacheEntry(name);
		long now = System.currentTimeMillis();
		long anHourAgo = now - TimeUnit.HOURS.toMillis(1);
		dce.setLastModified(Instant.ofEpochMilli(anHourAgo));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		dce.write(out
		byte[] raw = out.toByteArray();
		MessageDigest md0 = Constants.newMessageDigest();
		md0.update(raw);
		ByteArrayInputStream in = new ByteArrayInputStream(raw);
		MutableInteger infoAt = new MutableInteger();
		byte[] sharedInfo = new byte[raw.length];
		MessageDigest md = Constants.newMessageDigest();
		DirCacheEntry read = new DirCacheEntry(sharedInfo
				Instant.ofEpochMilli(now)
		assertEquals("Paths of length " + name.length() + " should match"
				read.getPathString());
		assertEquals("Should have been fully read"
		assertArrayEquals("Digests should match"
				md.digest());
	}

	@Test
	public void testLongPath() throws Exception {
		StringBuilder name = new StringBuilder(4094 + 16);
		for (int i = 0; i < 4094; i++) {
			name.append('a');
		}
		for (int j = 0; j < 16; j++) {
			checkPath(DirCacheVersion.DIRC_VERSION_EXTENDED
					name.toString());
			name.append('b');
		}
	}

	@Test
	public void testLongPathV4() throws Exception {
		StringBuilder name = new StringBuilder(4094 + 16);
		for (int i = 0; i < 4094; i++) {
			name.append('a');
		}
		DirCacheEntry previous = new DirCacheEntry(name.toString());
		for (int j = 0; j < 16; j++) {
			checkPath(DirCacheVersion.DIRC_VERSION_PATHCOMPRESS
					name.toString());
			name.append('b');
		}
	}

	@Test
	public void testShortPath() throws Exception {
		StringBuilder name = new StringBuilder(1 + 16);
		name.append('a');
		for (int j = 0; j < 16; j++) {
			checkPath(DirCacheVersion.DIRC_VERSION_EXTENDED
					name.toString());
			name.append('b');
		}
	}

	@Test
	public void testShortPathV4() throws Exception {
		StringBuilder name = new StringBuilder(1 + 16);
		name.append('a');
		DirCacheEntry previous = new DirCacheEntry(name.toString());
		for (int j = 0; j < 16; j++) {
			checkPath(DirCacheVersion.DIRC_VERSION_PATHCOMPRESS
					name.toString());
			name.append('b');
		}
	}

	@Test
	public void testPathV4() throws Exception {
		StringBuilder name = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			name.append('a');
		}
		DirCacheEntry previous = new DirCacheEntry(name.toString());
		for (int j = 0; j < 20; j++) {
			name.setLength(name.length() - 1);
			String newName = name.toString() + "bbb";
			checkPath(DirCacheVersion.DIRC_VERSION_PATHCOMPRESS
					newName);
		}
	}

