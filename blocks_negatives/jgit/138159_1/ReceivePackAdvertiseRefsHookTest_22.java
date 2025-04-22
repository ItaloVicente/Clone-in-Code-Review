		final TemporaryBuffer.Heap outBuf = new TemporaryBuffer.Heap(1024);
		final ReceivePack rp = new ReceivePack(dst);
		rp.setCheckReceivedObjects(true);
		rp.setCheckReferencedObjectsAreReachable(true);
		rp.setAdvertiseRefsHook(new HidePrivateHook());
		try {
			receive(rp, inBuf, outBuf);
			fail("Expected UnpackException");
		} catch (UnpackException failed) {
			Throwable err = failed.getCause();
			assertTrue(err instanceof MissingObjectException);
			MissingObjectException moe = (MissingObjectException) err;
			assertEquals(b, moe.getObjectId());
