			UploadPack up = uploadPackFactory.create(req
			try {
				up.sendAdvertisedRefs(pck);
			} finally {
				up.getRevWalk().release();
			}
