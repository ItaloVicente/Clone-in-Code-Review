	private long estimatePackSize() {
		long estSize = 0;
		for (BlockList<ObjectToPack> list : objectsLists) {
			if (list == null)
				continue;
			for (ObjectToPack otp : list) {
				if (otp.isReuseAsIs() || !otp.isDeltaRepresentation())
					estSize += otp.getWeight();
				else
					estSize += otp.getCachedSize();
			}
		}
		return estSize;
	}

