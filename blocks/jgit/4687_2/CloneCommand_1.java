
		Ref master = result.getAdvertisedRef(Constants.R_HEADS
				+ Constants.MASTER);
		if (master != null && master.getObjectId().equals(idHEAD.getObjectId()))
			return master;

