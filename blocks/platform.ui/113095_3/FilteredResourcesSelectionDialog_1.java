		private List<Position> getMatchPositions(String string, String matching) {
			final String originalMatching = matching;
			List<Position> positions = new ArrayList<>();
			if (matching.indexOf('?') == -1 && matching.indexOf('*') == -1) {
				matching = String.join("*", matching.split("(?=[A-Z0-9])")) + "*"; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			} else {
				matching = matching.toLowerCase();
				string = string.toLowerCase();
			}

