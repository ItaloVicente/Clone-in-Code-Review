		Map<String
		if (refPrefixes.isEmpty()) {
			refsToSend = getAdvertisedOrDefaultRefs();
		} else {
			refsToSend = new HashMap<>();
			for (String refPrefix : refPrefixes) {
				for (Ref ref : db.getRefDatabase().getRefsByPrefix(refPrefix)) {
					refsToSend.put(ref.getName()
				}
			}
		}

		if (needToFindSymrefs) {
			findSymrefs(adv
		}

		adv.send(refsToSend);
