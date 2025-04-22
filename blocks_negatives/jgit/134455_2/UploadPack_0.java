		Map<String, Ref> refsToSend;
		if (req.getRefPrefixes().isEmpty()) {
			refsToSend = getAdvertisedOrDefaultRefs();
		} else {
			refsToSend = new HashMap<>();
			for (String refPrefix : req.getRefPrefixes()) {
				for (Ref ref : db.getRefDatabase().getRefsByPrefix(refPrefix)) {
					refsToSend.put(ref.getName(), ref);
				}
			}
		}
