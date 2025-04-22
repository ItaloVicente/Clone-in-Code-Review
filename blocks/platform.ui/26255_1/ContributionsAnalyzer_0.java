	private static MMenuContribution mergeMenuContributions(
			ArrayList<MMenuContribution> contributionsToMerge) {

		List<MMenuContribution> orderedContributions = new ArrayList<MMenuContribution>();

		for (MMenuContribution contribution : contributionsToMerge) {
			if (hasAdditions(contribution)) {
				orderedContributions.add(0, contribution);
			} else {
				orderedContributions.add(contribution);
			}
		}

		MMenuContribution toContribute = null;
		for (MMenuContribution item : orderedContributions) {
			if (toContribute == null) {
				toContribute = item;
				continue;
			}
			int insertionIndex = getIndex(toContribute, item.getPositionInParent());
			Object[] array = item.getChildren().toArray();
			for (int c = 0; c < array.length; c++) {
				MMenuElement me = (MMenuElement) array[c];
				if (!containsMatching(toContribute.getChildren(), me)) {
					toContribute.getChildren().add(insertionIndex, me);
					insertionIndex++;
				}
			}
		}

		return toContribute;
	}

	private static boolean hasAdditions(MMenuContribution contribution) {
		for (MMenuElement child : contribution.getChildren()) {
			if (Util.equals(ADDITIONS, child.getElementId())) {
				return true;
			}
		}
		return false;
	}

