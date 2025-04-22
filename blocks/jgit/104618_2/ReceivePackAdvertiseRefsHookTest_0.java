	@Test
	public void resetsHaves() throws Exception {
		AtomicReference<Set<ObjectId>> haves = new AtomicReference<>();
		try (TransportLocal t = new TransportLocal(src
				dst.getDirectory()) {
			@Override
			ReceivePack createReceivePack(Repository db) {
				dst.incrementOpen();

				ReceivePack rp = super.createReceivePack(dst);
				rp.setAdvertiseRefsHook(new AdvertiseRefsHook() {
					@Override
					public void advertiseRefs(BaseReceivePack rp2)
							throws ServiceMayNotContinueException {
						rp.setAdvertisedRefs(rp.getRepository().getAllRefs()
								null);
						new HidePrivateHook().advertiseRefs(rp);
						haves.set(rp.getAdvertisedObjects());
					}

					@Override
					public void advertiseRefs(UploadPack uploadPack)
							throws ServiceMayNotContinueException {
						throw new UnsupportedOperationException();
					}
				});
				return rp;
			}
		}) {
			try (PushConnection c = t.openPush()) {
			}
		}

		assertEquals(1
		assertTrue(haves.get().contains(B));
		assertFalse(haves.get().contains(P));
	}

