
	@Test
	public void testReadValidLfsPointer() throws Exception {
				+ "oid sha256:" + TEST_SHA256 + '\n'
				+ "size 4\n";
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertEquals(lfs
		}
	}

	@Test
	public void testReadValidLfsPointerUnordered() throws Exception {
				+ "size 4\n"
				+ "oid sha256:" + TEST_SHA256 + '\n';
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertEquals(lfs
		}
	}

	@Test
	public void testReadValidLfsPointerVersionNotFirst() throws Exception {
		String ptr = "oid sha256:" + TEST_SHA256 + '\n'
				+ "size 4\n"
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertEquals(lfs
		}
	}

	@Test
	public void testReadInvalidLfsPointer() throws Exception {
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				cSource.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInvalidLfsPointer2() throws Exception {
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				cSource.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerVersionWrong() throws Exception {
				+ "oid sha256:" + TEST_SHA256 + '\n'
				+ "size 4\n";
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerVersionTwice() throws Exception {
				+ "oid sha256:" + TEST_SHA256 + '\n'
				+ "size 4\n";
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerVersionTwice2() throws Exception {
				+ "oid sha256:" + TEST_SHA256 + '\n'
				+ "size 4\n";
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerOidTwice() throws Exception {
				+ "oid sha256:" + TEST_SHA256 + '\n'
				+ "oid sha256:" + TEST_SHA256 + '\n'
				+ "size 4\n";
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerSizeTwice() throws Exception {
				+ "size 4\n"
				+ "size 4\n"
				+ "oid sha256:" + TEST_SHA256 + '\n';
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testRoundtrip() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer ptr = new LfsPointer(id
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ptr.encode(baos);
			try (ByteArrayInputStream in = new ByteArrayInputStream(
					baos.toByteArray())) {
				assertEquals(ptr
			}
		}
	}

	@Test
	public void testEquals() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs2 = new LfsPointer(id2
		assertTrue(lfs.equals(lfs2));
		assertTrue(lfs2.equals(lfs));
	}

	@Test
	public void testEqualsNull() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		assertFalse(lfs.equals(null));
	}

	@Test
	public void testEqualsSame() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		assertTrue(lfs.equals(lfs));
	}

	@Test
	public void testEqualsOther() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		assertFalse(lfs.equals(new Object()));
	}

	@Test
	public void testNotEqualsOid() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId
				.fromString(TEST_SHA256.replace('7'
		LfsPointer lfs2 = new LfsPointer(id2
		assertFalse(lfs.equals(lfs2));
		assertFalse(lfs2.equals(lfs));
	}

	@Test
	public void testNotEqualsSize() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs2 = new LfsPointer(id2
		assertFalse(lfs.equals(lfs2));
		assertFalse(lfs2.equals(lfs));
	}

	@Test
	public void testCompareToEquals() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs2 = new LfsPointer(id2
		assertEquals(0
		assertEquals(0
	}

	@Test
	@SuppressWarnings("SelfComparison")
	public void testCompareToSame() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		assertEquals(0
	}

	@Test
	public void testCompareToNotEqualsOid() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId
				.fromString(TEST_SHA256.replace('7'
		LfsPointer lfs2 = new LfsPointer(id2
		assertNotEquals(0
		assertNotEquals(0
	}

	@Test
	public void testCompareToNotEqualsSize() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs2 = new LfsPointer(id2
		assertNotEquals(0
		assertNotEquals(0
	}

	@Test
	public void testCompareToNull() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		assertThrows(NullPointerException.class
	}

	@Test
	public void testHashcodeEquals() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs2 = new LfsPointer(id2
		assertEquals(lfs.hashCode()
	}

	@Test
	public void testHashcodeSame() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		assertEquals(lfs.hashCode()
	}

	@Test
	public void testHashcodeNotEquals() throws Exception {
		AnyLongObjectId id = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(TEST_SHA256);
		LfsPointer lfs2 = new LfsPointer(id2
		assertNotEquals(lfs.hashCode()
	}
