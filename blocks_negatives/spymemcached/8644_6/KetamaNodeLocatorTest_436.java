	public void testContinuumWrapping() {
		setupNodes(4);
		/*
		String key="a";
		long lastKey=((KetamaNodeLocator)locator).getMaxKey();
		while(HashAlgorithm.KETAMA_HASH.hash(key) < lastKey) {
			key=PwGen.getPass(8);
		}
		System.out.println("Found a key past the end of the continuum:  "
			+ key);
		*/
		assertEquals(4294887009L, ((KetamaNodeLocator)locator).getMaxKey());
