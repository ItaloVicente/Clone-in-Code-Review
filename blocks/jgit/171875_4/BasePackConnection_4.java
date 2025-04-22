	static void updateWithSymRefsFromCapabilities(Map<String
		int resolvedCount = 0;
		List<String> unresolved = new ArrayList<>(capabilities.size());
		for (String option : capabilities) {
			if (option.startsWith(CAPABILITY_SYMREF_PREFIX)) {
				String[] symRef = option.substring(CAPABILITY_SYMREF_PREFIX.length()).split(":"
				if (symRef.length == 2 && !symRef[0].equals(symRef[1])) {
					Ref r = refMap.get(symRef[1]);
					if (r != null) {
						refMap.put(symRef[0]
						resolvedCount++;
					} else {
						unresolved.add(option);
					}
				}
			}
		}
		if (unresolved.size() > 0 && resolvedCount > 0) {
			updateWithSymRefsFromCapabilities(refMap
		}
	}

