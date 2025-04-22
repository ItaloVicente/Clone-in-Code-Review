	@Test
	public void testRefsChangedStackOverflow() throws Exception {
		final FileRepository newRepo = createBareRepository();
		final RefDatabase refDb = newRepo.getRefDatabase();
		File packedRefs = new File(newRepo.getDirectory()
		assertTrue(packedRefs.createNewFile());
		final AtomicReference<StackOverflowError> error = new AtomicReference<StackOverflowError>();
		final AtomicReference<IOException> exception = new AtomicReference<IOException>();
		final AtomicInteger changeCount = new AtomicInteger();
		newRepo.getListenerList().addRefsChangedListener(
				new RefsChangedListener() {

					public void onRefsChanged(RefsChangedEvent event) {
						try {
							refDb.getRefs("ref");
							changeCount.incrementAndGet();
						} catch (StackOverflowError soe) {
							error.set(soe);
						} catch (IOException ioe) {
							exception.set(ioe);
						}
					}
				});
		refDb.getRefs("ref");
		refDb.getRefs("ref");
		assertNull(error.get());
		assertNull(exception.get());
		assertEquals(1
	}

