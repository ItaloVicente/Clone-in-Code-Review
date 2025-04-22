		if (!getPreserveSelection()) {
			return;
		}
		if (checkStateProvider != null) {
			super.preservingSelection(updateCode);
			return;
		}

		int n = getItemCount(getControl());
		CustomHashtable checkedNodes = newHashtable(n * 2 + 1);
		CustomHashtable grayedNodes = newHashtable(n * 2 + 1);

		gatherState(checkedNodes, grayedNodes, getControl());

		super.preservingSelection(updateCode);

		applyState(checkedNodes, grayedNodes, getControl());
	}

	@Override
