		List<ObjectId> notAdvertisedWants = null;
		for (ObjectId obj : wantIds) {
			if (!advertised.contains(obj)) {
				if (notAdvertisedWants == null)
					notAdvertisedWants = new ArrayList<ObjectId>();
				notAdvertisedWants.add(obj);
			}
	    }
		if (notAdvertisedWants != null)
			requestValidator.checkWants(this

