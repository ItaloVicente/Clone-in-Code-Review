				ArrayList<String> sortedProposals = new ArrayList<String>(
						proposals);
				Collections
						.sort(sortedProposals, String.CASE_INSENSITIVE_ORDER);

				for (final String proposal : sortedProposals) {
