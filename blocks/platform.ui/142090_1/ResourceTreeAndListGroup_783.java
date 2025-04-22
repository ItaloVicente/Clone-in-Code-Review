			}
		}

		return false;
	}

	private boolean determineShouldBeWhiteChecked(Object treeElement) {
		return areAllChildrenWhiteChecked(treeElement)
				&& areAllElementsChecked(treeElement);
	}

	private void determineWhiteCheckedDescendents(Object treeElement) {
