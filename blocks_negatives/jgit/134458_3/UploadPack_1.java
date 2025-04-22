		Map<String, Ref> refsToSend;
		if (req.getRefPrefixes().isEmpty()) {
			refsToSend = getAdvertisedOrDefaultRefs();
		} else {
			refsToSend = new HashMap<>();
			String[] prefixes = req.getRefPrefixes().toArray(new String[0]);
			for (Ref ref : db.getRefDatabase().getRefsByPrefix(prefixes)) {
				refsToSend.put(ref.getName(), ref);
			}
		}
