			ReceivePack rp = receivePackFactory.create(req
			try {
				rp.sendAdvertisedRefs(pck);
			} finally {
				rp.getRevWalk().release();
			}
