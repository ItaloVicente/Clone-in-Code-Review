		Map<String
		if (refPrefixes.isEmpty()) {
			refsToSend = getAdvertisedOrDefaultRefs();
		} else {
			HashMap<String
			for (String refPrefix : refPrefixes) {
				int lastSlash = refPrefix.lastIndexOf('/');
				if (lastSlash == -1) {
					coarseRefs.putAll(getAdvertisedOrDefaultRefs());
					break;
				} else {
					String prefix = refPrefix.substring(0
					for (Map.Entry<String
							db.getRefDatabase().getRefs(prefix).entrySet()) {
						coarseRefs.put(prefix + entry.getKey()
					}
				}
			}

			refsToSend = new HashMap<>();
			for (Map.Entry<String
				for (String refPrefix : refPrefixes) {
					if (entry.getKey().startsWith(refPrefix)) {
						refsToSend.put(entry.getKey()
						continue;
					}
				}
			}
		}

		if (needToFindSymrefs) {
			findSymrefs(adv
		}

		adv.send(refsToSend);
