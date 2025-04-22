		if (current != null && !currentAlgorithms.isEmpty()) {
			currentAlgorithm = currentAlgorithms.poll();
			if (chosenAlgorithm != null) {
				Set<String> knownServerAlgorithms = session.getAttribute(
						JGitKexExtensionHandler.SERVER_ALGORITHMS);
				if (knownServerAlgorithms != null
						&& knownServerAlgorithms.contains(chosenAlgorithm)) {
					currentAlgorithm = null;
				}
			}
