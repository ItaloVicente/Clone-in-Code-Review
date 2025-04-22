
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
		try {
			lock.write("second".getBytes(StandardCharsets.US_ASCII));
			fail();
		} catch (Exception e) {
		}
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
		try {
			lock.write("second".getBytes(StandardCharsets.US_ASCII));
			fail();
		} catch (Exception e) {
		}
		lock.unlock();
	}

	@Test
	public void testLockWriteTwiceThrows3() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		lock.write("other".getBytes(StandardCharsets.US_ASCII));
		try (OutputStream out = lock.getOutputStream()) {
			out.write("second".getBytes(StandardCharsets.US_ASCII));
			fail();
		} catch (Exception e) {
		}
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
		try (OutputStream out = lock.getOutputStream()) {
			out.write("second".getBytes(StandardCharsets.US_ASCII));
			fail();
		} catch (Exception e) {
		}
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
			lock.commit();
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testLockNested() throws Exception {
		File f = writeTrashFile("somefile"
		LockFile lock = new LockFile(f);
		assertTrue(lock.lock());
		assertTrue(lock.isLocked());
		try {
			lock.lock();
			fail();
		} catch (IllegalStateException e) {
		}
		assertTrue(lock.isLocked());
		lock.unlock();
	}

	@Test
	public void testLockHeld() throws Exception {
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
