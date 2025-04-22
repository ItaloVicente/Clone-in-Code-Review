	static Map<String
		final Map<String
		for (String option : capabilities) {
			if (option.startsWith(CAPABILITY_SYMREF_PREFIX)) {
				String[] symRef = option
						.substring(CAPABILITY_SYMREF_PREFIX.length())
						.split(":"
				if (symRef.length == 2) {
					symRefs.put(symRef[0]
				}
			}
		}
		return symRefs;
	}

	static void updateWithSymRefs(Map<String
		boolean haveNewRefMapEntries = !refMap.isEmpty();
		while (!symRefs.isEmpty() && haveNewRefMapEntries) {
			haveNewRefMapEntries = false;
			final Iterator<Map.Entry<String
			while (iterator.hasNext()) {
				final Map.Entry<String
					final Ref r = refMap.get(symRef.getValue());
					if (r != null) {
						refMap.put(symRef.getKey()
						haveNewRefMapEntries = true;
						iterator.remove();
					}
				}
			}
		}
	}

