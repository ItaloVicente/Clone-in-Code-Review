
	@Test
	public void testReadValidLfsPointer() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
				+ "oid sha256:" + s + '\n' + "size 4\n";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertEquals(lfs
		}
	}

	@Test
	public void testReadValidLfsPointerUnordered() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
				+ "oid sha256:" + s + '\n';
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertEquals(lfs
		}
	}

	@Test
	public void testReadValidLfsPointerVersionNotFirst() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		String ptr = "oid sha256:" + s + '\n' + "size 4\n"
		AnyLongObjectId id = LongObjectId.fromString(s);
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
	public void testReadInValidLfsPointerVersionWrong() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
				+ "oid sha256:" + s + '\n' + "size 4\n";
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerVersionTwice() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
				+ "oid sha256:" + s + '\n' + "size 4\n";
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerVersionTwice2() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
				+ "oid sha256:" + s + '\n'
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerOidTwice() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
				+ "oid sha256:" + s + '\n' + "oid sha256:" + s + '\n'
				+ "size 4\n";
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testReadInValidLfsPointerSizeTwice() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
				+ "size 4\n" + "oid sha256:" + s + '\n';
		try (ByteArrayInputStream in = new ByteArrayInputStream(
				ptr.getBytes(UTF_8))) {
			assertNull("Is not a LFS pointer"
		}
	}

	@Test
	public void testRoundtrip() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
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
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(s);
		LfsPointer lfs2 = new LfsPointer(id2
		assertTrue(lfs.equals(lfs2));
		assertTrue(lfs2.equals(lfs));
	}

	@Test
	public void testEqualsNull() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		assertFalse(lfs.equals(null));
	}

	@Test
	public void testEqualsSame() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		assertTrue(lfs.equals(lfs));
	}

	@Test
	public void testEqualsOther() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		assertFalse(lfs.equals(new Object()));
	}

	@Test
	public void testNotEqualsOid() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(s.replace('7'
		LfsPointer lfs2 = new LfsPointer(id2
		assertFalse(lfs.equals(lfs2));
		assertFalse(lfs2.equals(lfs));
	}

	@Test
	public void testNotEqualsSize() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(s);
		LfsPointer lfs2 = new LfsPointer(id2
		assertFalse(lfs.equals(lfs2));
		assertFalse(lfs2.equals(lfs));
	}

	@Test
	public void testCompareToEquals() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(s);
		LfsPointer lfs2 = new LfsPointer(id2
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testCompareToSame() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		assertEquals(0
	}

	@Test
	public void testCompareToNotEqualsOid() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(s.replace('7'
		LfsPointer lfs2 = new LfsPointer(id2
		assertNotEquals(0
		assertNotEquals(0
	}

	@Test
	public void testCompareToNotEqualsSize() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(s);
		LfsPointer lfs2 = new LfsPointer(id2
		assertNotEquals(0
		assertNotEquals(0
	}

	@Test
	public void testCompareToNull() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		assertNotEquals(0
	}

	@Test
	public void testHashcodeEquals() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(s);
		LfsPointer lfs2 = new LfsPointer(id2
		assertEquals(lfs.hashCode()
	}

	@Test
	public void testHashcodeSame() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		assertEquals(lfs.hashCode()
	}

	@Test
	public void testHashcodeNotEquals() throws Exception {
		String s = "27e15b72937fc8f558da24ac3d50ec20302a4cf21e33b87ae8e4ce90e89c4b10";
		AnyLongObjectId id = LongObjectId.fromString(s);
		LfsPointer lfs = new LfsPointer(id
		AnyLongObjectId id2 = LongObjectId.fromString(s);
		LfsPointer lfs2 = new LfsPointer(id2
		assertNotEquals(lfs.hashCode()
	}
