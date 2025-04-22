
	@Test
	public void testLockTwice() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		lock.write("other".getBytes(StandardCharsets.US_ASCII));
		lock.commit();
		assertFalse(lock.isLocked());
		checkFile(f
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		try (OutputStream out = lock.getOutputStream()) {
			out.write("second".getBytes(StandardCharsets.US_ASCII));
		}
		lock.commit();
		assertFalse(lock.isLocked());
		checkFile(f
	}

	@Test
	public void testLockTwiceUnlock() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		lock.write("other".getBytes(StandardCharsets.US_ASCII));
		lock.unlock();
		assertFalse(lock.isLocked());
		checkFile(f
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		try (OutputStream out = lock.getOutputStream()) {
			out.write("second".getBytes(StandardCharsets.US_ASCII));
		}
		lock.commit();
		assertFalse(lock.isLocked());
		checkFile(f
	}

	@Test
	public void testLockWriteTwiceThrows1() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		lock.write("other".getBytes(StandardCharsets.US_ASCII));
		assertThrows(Exception.class
				() -> lock.write("second".getBytes(StandardCharsets.US_ASCII)));
		lock.unlock();
	}

	@Test
	public void testLockWriteTwiceThrows2() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		try (OutputStream out = lock.getOutputStream()) {
			out.write("other".getBytes(StandardCharsets.US_ASCII));
		}
		assertThrows(Exception.class
				() -> lock.write("second".getBytes(StandardCharsets.US_ASCII)));
		lock.unlock();
	}

	@Test
	public void testLockWriteTwiceThrows3() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		lock.write("other".getBytes(StandardCharsets.US_ASCII));
		assertThrows(Exception.class
			try (OutputStream out = lock.getOutputStream()) {
				out.write("second".getBytes(StandardCharsets.US_ASCII));
			}
		});
		lock.unlock();
	}

	@Test
	public void testLockWriteTwiceThrows4() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		try (OutputStream out = lock.getOutputStream()) {
			out.write("other".getBytes(StandardCharsets.US_ASCII));
		}
		assertThrows(Exception.class
			try (OutputStream out = lock.getOutputStream()) {
				out.write("second".getBytes(StandardCharsets.US_ASCII));
			}
		});
		lock.unlock();
	}

	@Test
	public void testLockUnclosedCommitThrows() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		try (OutputStream out = lock.getOutputStream()) {
			out.write("other".getBytes(StandardCharsets.US_ASCII));
			assertThrows(Exception.class
		}
	}

	@Test
	public void testLockHeld1() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		assertFalse(lock.lock());
		assertTrue(lock.isLocked());
		lock.unlock();
	}

	@Test
	public void testLockHeld2() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		LockFile lock2 = new LockFile(f);
		assertFalse(lock2.lock());
		assertFalse(lock2.isLocked());
		assertTrue(lock.isLocked());
		lock.unlock();
	}

	@Test
	public void testLockForAppend() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lockForAppend());
		assertTrue(lock.isLocked());
		lock.write("other".getBytes(StandardCharsets.US_ASCII));
		lock.commit();
		assertFalse(lock.isLocked());
		checkFile(f
	}
