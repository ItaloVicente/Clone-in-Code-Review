			}
		}
		Collections.sort(selectedDirectories);
		for (String prefix : selectedDirectories) {
			Iterator<String> iterator = mayBeCommitted.iterator();
			while (iterator.hasNext()) {
				String candidate = iterator.next();
				if (candidate.startsWith(prefix)) {
					iterator.remove();
					preselectionCandidates.add(candidate);
