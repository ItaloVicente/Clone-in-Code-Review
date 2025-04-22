		PackFile packFile = new PackFile(packName
		try {
			packFile.get(wc
			fail("expected LargeObjectException.ExceedsByteArrayLimit");
		} catch (LargeObjectException.ExceedsByteArrayLimit bad) {
			assertNull(bad.getObjectId());
		} finally {
			packFile.close();
		}
