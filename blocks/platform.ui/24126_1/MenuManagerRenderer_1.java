	private int findBestFittingIndex(IContributionItem[] existingContributions,
			MMenuElement newElement) {

		if (existingContributions == null || existingContributions.length <= 0
				|| newElement == null)
			return -1;

		MElementContainer<MUIElement> parent = newElement.getParent();
		if (parent != null) {
			int insertIndex = -1;
			List<MUIElement> children = parent.getChildren();
			List<IContributionItem> existingContributionsAsList = Arrays
					.asList(existingContributions);

			for (int searchIndex = children.indexOf(newElement) - 1; searchIndex >= 0; searchIndex--) {
				MUIElement sibling = children.get(searchIndex);

				Object opaqueItem = OpaqueElementUtil.getOpaqueItem(sibling);
				if (opaqueItem != null) {
					insertIndex = existingContributionsAsList
							.indexOf(opaqueItem);
				} else {
					insertIndex = findById(existingContributionsAsList,
							sibling.getElementId(), searchIndex);
				}

				if (insertIndex >= 0) {
					return insertIndex + 1; // insert after
				}
			}
		}

		return -1;
	}

	private int findById(List<IContributionItem> existingContributions,
			String id,
			int feasibleIndex) {


		for (int i = Math.min(existingContributions.size() - 1,
				Math.max(0, feasibleIndex)); i >= 0; i--) {
			String contribID = existingContributions.get(i).getId();
			if (id == contribID || (id != null && id.equals(contribID))) {
				return i;
			}
		}

		return -1;
	}

