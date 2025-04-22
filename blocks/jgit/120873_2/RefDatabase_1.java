	@NonNull
	public List<Ref> getRefsByPrefix(String prefix) throws IOException {
		Map<String
		int lastSlash = prefix.lastIndexOf('/');
		if (lastSlash == -1) {
			coarseRefs = getRefs(ALL);
		} else {
			coarseRefs = getRefs(prefix.substring(0
		}

		String unmatchedPrefix = prefix.substring(lastSlash + 1);
		if (unmatchedPrefix.isEmpty()) {
			return new ArrayList<>(coarseRefs.values());
		}
		ArrayList<Ref> filteredRefs = new ArrayList<>();
		for (Map.Entry<String
			if (entry.getKey().startsWith(unmatchedPrefix)) {
				filteredRefs.add(entry.getValue());
			}
		}
		return Collections.unmodifiableList(filteredRefs);
	}

