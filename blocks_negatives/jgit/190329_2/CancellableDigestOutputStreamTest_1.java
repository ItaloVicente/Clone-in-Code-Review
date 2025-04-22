		assertTrue(out.length() > BYTES_TO_WRITE_BEFORE_CANCEL_CHECK);
		m.setCancelled(true);

		for (int i = 0; i < triggerCancelWriteCnt - 1; i++) {
			out.write(KB);
		}

		long lastLength = out.length();
		assertThrows(InterruptedIOException.class, () -> {
			out.write(1);
		});
		assertEquals(lastLength, out.length());

		assertThrows(InterruptedIOException.class, () -> {
			out.write(new byte[1]);
		});
		assertEquals(lastLength, out.length());
