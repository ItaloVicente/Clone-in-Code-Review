		if (!refPrefixes.isEmpty()) {
			HashMap<String
			for (Map.Entry<String
				for (String refPrefix : refPrefixes) {
					if (entry.getKey().startsWith(refPrefix)) {
						refsToSend.put(entry.getKey()
						continue;
					}
				}
			}
			refs = refsToSend;
		}

