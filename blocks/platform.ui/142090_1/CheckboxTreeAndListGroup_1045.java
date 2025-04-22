		}

		return false;
	}

	protected boolean determineShouldBeWhiteChecked(Object treeElement) {
		return areAllChildrenWhiteChecked(treeElement)
				&& areAllElementsChecked(treeElement);
	}

	protected void determineWhiteCheckedDescendents(Object treeElement) {
