		if (head == null) {
			head = result.getAdvertisedRef(branch);
			if (head == null)
				head = result.getAdvertisedRef(Constants.R_HEADS + branch);
			if (head == null)
				head = result.getAdvertisedRef(Constants.R_TAGS + branch);
		}
