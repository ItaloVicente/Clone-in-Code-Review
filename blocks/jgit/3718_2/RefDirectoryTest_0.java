	@Test
	public void testGetRefs_LooseSorting_Bug_348834() throws IOException {
		Map<String

		writeLooseRef("refs/heads/my/a+b"
		writeLooseRef("refs/heads/my/a/b/c"

		final int[] count = new int[1];

		ListenerHandle listener = Repository.getGlobalListenerList()
				.addRefsChangedListener(new RefsChangedListener() {

					public void onRefsChanged(RefsChangedEvent event) {
						count[0]++;
					}
				});

		refs = refdir.getRefs(RefDatabase.ALL);
		refs = refdir.getRefs(RefDatabase.ALL);
		listener.remove();
		assertEquals(1
		assertEquals(2
		assertEquals(A
		assertEquals(B

	}

