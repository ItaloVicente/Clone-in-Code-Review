			}
		}
		Collections.sort(selectedDirectories);
		String lastAncestor = null;
		for (String prefix : selectedDirectories) {
			if (lastAncestor != null && prefix.startsWith(lastAncestor)) {
				continue;
			}
			Iterator<String> iterator = mayBeCommitted.iterator();
			while (iterator.hasNext()) {
				String candidate = iterator.next();
				if (candidate.startsWith(prefix)) {
					iterator.remove();
					preselectionCandidates.add(candidate);
