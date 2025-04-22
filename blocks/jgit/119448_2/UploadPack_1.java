		if (refPrefix != null) {
			HashMap<String
			for (Map.Entry<String
				if (!entry.getKey().startsWith(refPrefix)) {
					continue;
				}
				refsToSend.put(entry.getKey()
			}
			refs = refsToSend;
		}

